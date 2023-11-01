import java.io.*;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Main {
    private static int N;
    private static int M;
    private static int n;
    private static int m;
    private static int p;
    private static int[][] matrix;
    private static int[][] kernel;

    private static CyclicBarrier barrier;

    public static void read(String path) {
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);

            if (myReader.hasNextLine()) {
                N = Integer.parseInt(myReader.nextLine());
                M = Integer.parseInt(myReader.nextLine());
            }
            matrix = new int[N][M];
            if (myReader.hasNextLine()) {
                for (int i = 0; i < N; i++) {
                    String data = myReader.nextLine();
                    String[] line = data.split(" ");
                    for (int j = 0; j < M; j++) {
                        matrix[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }

            if (myReader.hasNextLine()) {
                n = Integer.parseInt(myReader.nextLine());
                m = Integer.parseInt(myReader.nextLine());
            }
            kernel = new int[n][m];
            if (myReader.hasNextLine()) {
                for (int i = 0; i < n; i++) {
                    String data = myReader.nextLine();
                    String[] line = data.split(" ");
                    for (int j = 0; j < m; j++) {
                        kernel[i][j] = Integer.parseInt(line[j]);
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void write(String path, int[][] matrix) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(path));
            for (int[] elem : matrix) {
                for (int i : elem) {
                    bw.write(i + " ");
                }
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        read("input.txt");
        p = Integer.parseInt(args[0]);

        if (p != 0) {
            barrier = new CyclicBarrier(p);
        }

        if (p == 0)
            sequentialConvolution();
        else
            parallelConvolution();

        write("output.txt", matrix);

        if (p == 0) {
            write("valid.txt", matrix);
        } else {
            checkCompliance("output.txt", "valid.txt");
        }
    }

    private static void checkCompliance(String pathTest, String pathValid) throws Exception {
        File objTest = new File(pathTest);
        Scanner readerTest = new Scanner(objTest);

        File objValid = new File(pathValid);
        Scanner readerValid = new Scanner(objValid);

        while (readerTest.hasNextLine() && readerValid.hasNextLine()) {
            String test = readerTest.nextLine();
            String valid = readerValid.nextLine();

            if (!valid.equals(test)) {
                throw new Exception("Invalid output");
            }
        }

        if (readerTest.hasNextLine() || readerValid.hasNextLine()) {
            throw new Exception("Invalid output");
        }
    }

    public static void sequentialConvolution() {
        long startTime = System.nanoTime();

        int[] previousLine = new int[M];
        int[] currentLine = new int[M];

        System.arraycopy(matrix[0], 0, previousLine, 0, M);
        System.arraycopy(matrix[0], 0, currentLine, 0, M);

        for (int i = 0; i < N; i++) {
            int[] buffer = new int[M];
            for (int j = 0; j < M; j++) {
                int output;
                output = compute(previousLine, j, 0) + compute(currentLine, j, 1) + compute(matrix[min(N - 1, i + 1)], j, 2);

                buffer[j] = output;
            }

            System.arraycopy(buffer, 0, matrix[i], 0, M);
            System.arraycopy(currentLine, 0, previousLine, 0, currentLine.length);
            System.arraycopy(matrix[min(N - 1, i + 1)], 0, currentLine, 0, currentLine.length);
        }

        long endTime = System.nanoTime();
        System.out.println((double)(endTime - startTime) / 1E6);
    }

    public static void parallelConvolution() throws InterruptedException {
        Thread[] t = new Worker[p];

        int start, end = 0;
        int chunk = N / p;
        int rest = N % p;

        long startTime = System.nanoTime();

        for (int i = 0; i < t.length; i++) {
            start = end;
            end = start + chunk;
            if (rest > 0) {
                end++;
                rest--;
            }
            t[i] = new Worker(start, end);
            t[i].start();
        }

        for (Thread thread : t) {
            thread.join();
        }

        long stopTime = System.nanoTime();

        System.out.println((double)(stopTime - startTime) / 1E6);
    }

    public static class Worker extends Thread {
        int start, end;

        public Worker(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public void run() {
            int[] previousLine = new int[M];
            int[] currentLine = new int[M];

            System.arraycopy(matrix[Math.max(start - 1, 0)], 0, previousLine, 0, M);
            System.arraycopy(matrix[start], 0, currentLine, 0, M);

            int[] bufferUp = new int[M];
            int[] bufferDown = new int[M];

            for (int i = start; i < end; i++) {
                for (int j = 0; j < M; j++) {
                    int output;
                    output = compute(previousLine, j, 0) + compute(currentLine, j, 1) + compute(matrix[min(N - 1, i + 1)], j, 2);

                    if (i == start) {
                        bufferUp[j] = output;
                    } else if (i == end - 1) {
                        bufferDown[j] = output;
                    } else {
                        matrix[i][j] = output;
                    }
                }

                System.arraycopy(currentLine, 0, previousLine, 0, currentLine.length);
                System.arraycopy(matrix[min(N - 1, i + 1)], 0, currentLine, 0, currentLine.length);
            }

            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException ignored) {}

            System.arraycopy(bufferUp, 0, matrix[start], 0, M);
            System.arraycopy(bufferDown, 0, matrix[end - 1], 0, M);
        }
    }

    private static int compute(int[] values, int j, int kernelRow) {
        return values[max(j - 1, 0)] * kernel[kernelRow][0] + values[j] * kernel[kernelRow][1] + values[min(M - 1, j + 1)] * kernel[kernelRow][2];
    }
}
