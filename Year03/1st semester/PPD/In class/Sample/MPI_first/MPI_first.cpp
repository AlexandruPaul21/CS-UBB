#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>
#include <random>
#include <iostream>

using namespace std;

void printVector(int v[], int n) {
    for (int i = 0; i < n; i++) {
        cout << v[i] << " ";
    }
    cout << endl;
}

int main(int argc, char** argv) {
    const int n = 10;
    int a[n], b[n], c[n];

    MPI_Init(NULL, NULL);
    
    int world_size;
    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
    
    int world_rank;
    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);

    int* auxA = new int[n / world_size];
    int* auxB = new int[n / world_size];
    int* auxC = new int[n / world_size];

    if (world_rank == 0) {
        for (int i = 0; i < n; i++) {
            a[i] = rand() % 10;
            b[i] = rand() % 10;
        }
    }

    MPI_Scatter(a, n / world_size, MPI_INT, auxA, n / world_size, MPI_INT, 0, MPI_COMM_WORLD);
    MPI_Scatter(b, n / world_size, MPI_INT, auxB, n / world_size, MPI_INT, 0, MPI_COMM_WORLD);

    for (int i = 0; i < n / world_size; ++i) {
        auxC[i] = auxA[i] + auxB[i];
    }

    MPI_Gather(auxC, n / world_size, MPI_INT, c, n / world_size, MPI_INT, 0, MPI_COMM_WORLD);
    
    if (world_rank == 0) {
        printVector(a, n);
        printVector(b, n);
        printVector(c, n);
    }

    MPI_Finalize();
}

//int main(int argc, char** argv) {
//    const int n = 10;
//    int a[n], b[n], c[n];
//    int start, end;
//    MPI_Status status;
//
//    MPI_Init(NULL, NULL);
//
//    int world_size;
//    MPI_Comm_size(MPI_COMM_WORLD, &world_size);
//
//    int world_rank;
//    MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
//
//    if (world_rank == 0) { // master
//        for (int i = 0; i < n; i++) {
//            a[i] = rand() % 10;
//            b[i] = rand() % 10;
//        }
//
//        int size = n / (world_size - 1);
//        int rest = n % (world_size - 1);
//        start = 0;
//        end = 0;
//
//        for (int i = 1; i < world_size; ++i) {
//            start = end;
//            end = start + size;
//            if (rest > 0) {
//                --rest;
//                ++end;
//            }
//
//            MPI_Send(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(a + start, (end - start), MPI_INT, i, 0, MPI_COMM_WORLD);
//            MPI_Send(b + start, (end - start), MPI_INT, i, 0, MPI_COMM_WORLD);
//        }
//
//        for (int i = 1; i < world_size; ++i) {
//            MPI_Recv(&start, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//            MPI_Recv(&end, 1, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//            MPI_Recv(c + start, end - start, MPI_INT, i, 0, MPI_COMM_WORLD, &status);
//        }
//
//        printVector(a, n);
//        printVector(b, n);
//        printVector(c, n);
//    }
//    else {
//        MPI_Recv(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//        MPI_Recv(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//        MPI_Recv(a + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//        MPI_Recv(b + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
//
//        // printf("Primit (nr: %d) valorile start: %d, end: %d\n", world_rank, start, end);
//
//        for (int i = start; i < end; ++i) {
//            c[i] = a[i] + b[i];
//        }
//
//        MPI_Send(&start, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//        MPI_Send(&end, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
//        MPI_Send(c + start, end - start, MPI_INT, 0, 0, MPI_COMM_WORLD);
//
//    }
//
//    MPI_Finalize();
//}
