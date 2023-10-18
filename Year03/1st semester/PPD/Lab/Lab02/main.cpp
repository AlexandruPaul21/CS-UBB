#include <iostream>
#include <thread>
#include <random>

using namespace std;

const int MAX_LENGTH = 100;
const int MAX_THREADS = 10;
const int VAL_MAX = 100;
const int ACTUAL_LENGTH = 10;

int a[MAX_LENGTH], b[MAX_LENGTH], c[MAX_LENGTH];

void populateArray(int n, int *p, int *q) {
    if (n > MAX_LENGTH) {
        n = MAX_LENGTH;
    }
    srand(time(nullptr));

    for (int i = 0; i < n; ++i) {
        p[i] = rand() % VAL_MAX;
        q[i] = rand() % VAL_MAX;
    }
}

void work(int threadNo, int *p, int *q, int *r) {
    for (int i = threadNo; i < ACTUAL_LENGTH; i += MAX_THREADS) {
        r[i] = p[i] + q[i];
    }
}

void printArray(int *arr, int length) {
    for (int i = 0; i < length; ++i) {
        cout << arr[i] << " ";
    }
    cout << endl;
}

void staticInit() {
    populateArray(ACTUAL_LENGTH, a, b);
}

int main() {
    // staticInit();

    int* x = new int[ACTUAL_LENGTH];
    int* y = new int[ACTUAL_LENGTH];
    int* z = new int[ACTUAL_LENGTH];

    populateArray(ACTUAL_LENGTH, x, y);

    thread threads[MAX_THREADS];

    for (int i = 0; i < MAX_THREADS; ++i) {
        threads[i] = thread(work, i, x, y, z);
        // threads[i] = thread(work, i, a, b, c);
    }

    for (int i = 0; i < MAX_THREADS; ++i) {
        threads[i].join();
    }

    printArray(x, ACTUAL_LENGTH);
    printArray(y, ACTUAL_LENGTH);
    printArray(z, ACTUAL_LENGTH);

    delete[] x;
    delete[] y;
    delete[] z;

    return 0;
}
