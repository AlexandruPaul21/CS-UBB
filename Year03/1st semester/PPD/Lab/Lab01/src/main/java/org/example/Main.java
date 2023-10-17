package org.example;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static class MyThread extends Thread {
        int noThreads;
        int id;
        int start;
        int end;

        public MyThread(int id, int noThreads, int start, int end) {
            this.id = id;
            this.noThreads = noThreads;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i <= end; ++i) {
                C[i] = A[i] + B[i];
            }
            int pos = N - N % noThreads + id;
            if (pos < N) {
                C[pos] = A[pos] + B[pos];
            }
        }
    }

    private static class MyThread2 extends Thread {
        int noThreads;
        int id;

        public MyThread2(int noThreads, int id) {
            this.noThreads = noThreads;
            this.id = id;
        }

        @Override
        public void run() {
            for (int i = id; i < A.length; i += noThreads) {
                C[i] = A[i] + B[i];
            }
        }
    }

    static int N = 100;
    static int[] A = new int[N];
    static int[] B = new int[N];
    static int[] C = new int[N];

    private static void initArray() {
        Random rand = new Random();

        for (int i = 0; i < A.length; i++) {
            A[i] = rand.nextInt(100) + 1;
            B[i] = rand.nextInt(100) + 1;
            C[i] = 0;
        }
    }

    public static void main(String[] args) {
        initArray();

        System.out.println(Arrays.toString(A));
        System.out.println(Arrays.toString(B));

        int p = 12;
        Thread[] threads = new MyThread2[p];

        long start_t = System.currentTimeMillis();
        for (int i = 0; i < p; ++i) {
            int start = N / p * i;
            int end = N / p * (i + 1) - 1;
            threads[i] = new MyThread2(p, i);
            threads[i].start();
        }

        for (int i = 0; i < p; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Parallel time: " + (System.currentTimeMillis() - start_t));
         System.out.println(Arrays.toString(C));
    }
}
