#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <random>
#include <iostream>
#include <fstream>
#include <chrono>

using namespace std;
using namespace std::chrono;

int N, M, n, m;
int matrix[1000][1000];
int kernel[3][3];
int finalMatrix[1000][1000];

int singlePixelConvolution(int x, int y, int offset) {
    int output = 0;
    int n = 3;
    int m = 3;

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            int ii = x - offset + i;
            int jj = y - offset + j;

            if (ii < 0) ii = 0;
            else if (ii >= N) ii = N - 1;

            if (jj < 0) jj = 0;
            else if (jj >= M) jj = M - 1;

            output += matrix[ii][jj] * kernel[i][j];
        }
    }

    return output;
}

void check_compliance(string path_t, string path_v) {
    ifstream fin_t(path_t);
    ifstream fin_v(path_v);
    int x, y;
    while (fin_t >> x && fin_v >> y) {
        if (x != y) {
            throw exception();
        }
    }

    if (fin_t >> x || fin_v >> x) {
        throw exception();
    }
}

int main(int argc, char** argv) {
    int linesPerProcess;
    MPI_Status status;

    MPI_Init(NULL, NULL);

    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);

    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

    if (world_rank == 0) { // master
        auto start = high_resolution_clock::now();

        ifstream fin("input.txt");
        fin >> n >> m;

        MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&m, 1, MPI_INT, 0, MPI_COMM_WORLD);

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                fin >> kernel[i][j];
            }
        }

        MPI_Bcast(&kernel, n * m, MPI_INT, 0, MPI_COMM_WORLD);

        fin >> N >> M;

        MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&M, 1, MPI_INT, 0, MPI_COMM_WORLD);

        linesPerProcess = N / (world_size - 1);
        auto calcStart = high_resolution_clock::now();

        for (int i = 0; i <= N; ++i) {
            if (i != N) {
                for (int j = 0; j < M; ++j) {
                    fin >> matrix[i][j];
                }
            }
            if (i % linesPerProcess == 0 && i != 0) {
                int actualChunk = i / linesPerProcess;
                MPI_Send(matrix + max((actualChunk - 1) * linesPerProcess - 1, 0), (linesPerProcess + 2) * M, MPI_INT, actualChunk, 0, MPI_COMM_WORLD);
            }
        }

        ofstream fout("output.txt");

        for (int k = 1; k < world_size; ++k) {
            MPI_Recv(finalMatrix + (k - 1) * linesPerProcess, linesPerProcess * M, MPI_INT, k, 0, MPI_COMM_WORLD, &status);
            for (int i = (k - 1) * linesPerProcess; i < k * linesPerProcess; i++) {
                for (int j = 0; j < M; j++) {
                    fout << finalMatrix[i][j] << " ";
                }
                fout << endl;
            }
        }
        auto calcEnd = high_resolution_clock::now();
        cout << "T2: " << duration_cast<milliseconds>(calcEnd - calcStart).count() << endl;
        fout.close();
        auto end = high_resolution_clock::now();
        cout << "T1: " << duration_cast<milliseconds>(end - start).count() << endl;        
        check_compliance("output.txt", "valid.txt");
    }
    else {
        MPI_Bcast(&n, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&m, 1, MPI_INT, 0, MPI_COMM_WORLD);

        MPI_Bcast(&kernel, n * m, MPI_INT, 0, MPI_COMM_WORLD);

        MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
        MPI_Bcast(&M, 1, MPI_INT, 0, MPI_COMM_WORLD);

        linesPerProcess = N / (world_size - 1);

        MPI_Recv(matrix + max((world_rank - 1) * linesPerProcess - 1, 0), (linesPerProcess + 2) * M, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

        for (int i = (world_rank - 1) * linesPerProcess; i < world_rank * linesPerProcess; ++i) {
            for (int j = 0; j < M; ++j) {
                finalMatrix[i][j] = singlePixelConvolution(i, j, 1);
            }
        }

        MPI_Send(finalMatrix + (world_rank - 1) * linesPerProcess, linesPerProcess * M, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }
    MPI_Finalize();
}
