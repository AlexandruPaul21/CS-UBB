#include <iostream>
#include <fstream>
#include <thread>
#include <vector>

using namespace std;
using namespace std::chrono;

const int N_MAX = 1000;
const int M_MAX = 1000;
const int K_MAX = 6;

int N, M, n, m, p;

int matrix[N_MAX][M_MAX];
int kernel[K_MAX][K_MAX];
int finalMatrix[N_MAX][M_MAX];

void read(string path) {
    ifstream fin(path);

    fin >> N >> M;

    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < M; ++j) {
            fin >> matrix[i][j];
        }
    }

    fin >> n >> m;

    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < m; ++j) {
            fin >> kernel[i][j];
        }
    }

    fin.close();
}

void write(string path) {
    ofstream fout(path);

    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            fout << finalMatrix[i][j] << " ";
        }
        fout << endl;
    }

    fout.close();
}

int singlePixelConvolution(int x, int y, int offset) {
    int output = 0;

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

void sequentialConvolution(int offset) {
    auto startTime = high_resolution_clock::now();
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            finalMatrix[i][j] = singlePixelConvolution(i, j, offset);
        }
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

void parallelConvolution(int offset, int start, int end) {
    if (N > M) {
        for (int i = start; i < end; i++) {
            for (int j = 0; j < M; j++) {
                finalMatrix[i][j] = singlePixelConvolution(i, j, offset);
            }
        }
    }
    else {
        for (int i = 0; i < N; i++) {
            for (int j = start; j < end; j++) {
                finalMatrix[i][j] = singlePixelConvolution(i, j, offset);
            }
        }
    }
}

void parallelization(int offset) {
    vector<thread> t;

    int start = 0, end = 0;
    int chunk = N / p;
    int rest = N % p;

    auto startTime = high_resolution_clock::now();

    for (size_t i = 0; i < p; i++){
        start = end;
        end = start + chunk + (rest-- > 0);
        if (rest > 0)
        {
            end++;
            rest--;
        }
        thread thr = thread(parallelConvolution, offset, start, end);
        t.push_back(std::move(thr));
        start = end;
    }

    for (auto& th : t)
    {
        if (th.joinable())
            th.join();
    }

    auto endTime = high_resolution_clock::now();

    double difference = duration<double, milli>(endTime - startTime).count();

    cout << difference << endl;
}

int main(int argc, char** argv) {
    p = atoi(argv[1]);

    read("input.txt");

    int offset = (n - 1) / 2;

    if (p == 0) {
        sequentialConvolution(offset);
    } else {
        parallelization(offset);
    }

    write("output.txt");
}
