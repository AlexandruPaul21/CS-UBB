package org.example;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static final AtomicInteger readers = new AtomicInteger();
    private static final LinkedList<Node> sequentialResults = new LinkedList<>();
    private static final MyLinkedList results = new MyLinkedList();
    private static final MyQueue queue = new MyQueue(readers);

    private static final List<String> files = new ArrayList<>();

    private static void computeSequentially() {
        for (String file : files) {
            try {
                File obj = new File("src/main/resources/input_files/" + file);
                Scanner scanner = new Scanner(obj);

                while (scanner.hasNextLine()) {
                    String[] result = scanner.nextLine().split(",");

                    process(result[0], Integer.parseInt(result[1]));

                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static void process(String id, Integer points) {
        boolean finished = true;
        for (int i = 0; i < sequentialResults.size(); ++i) {
            Node actualNode = sequentialResults.get(i);
            if (actualNode.getId().equals(id)) {
                if (actualNode.getPoints() == -1) {
                    finished = false;
                    break;
                }

                if (points == -1) {
                    Node node = new Node(id, -1);
                    sequentialResults.remove(i);
                    sequentialResults.add(node);
                    finished = false;
                    break;
                }

                Node node = new Node(id, actualNode.getPoints() + points);

                while (i > 1 && node.getPoints() > sequentialResults.get(i - 1).getPoints()) {
                    sequentialResults.set(i, sequentialResults.get(i - 1));
                    --i;
                }

                sequentialResults.set(i, node);
                finished = false;
                break;
            }
        }

        if (finished) {
            int i = 0;
            Node node = new Node(id, points);
            while (i < sequentialResults.size()) {
                if (sequentialResults.get(i).getPoints() <= node.getPoints()) {
                    break;
                }
                ++i;
            }
            sequentialResults.add(i, node);
        }
    }

    public static void main(String[] args) {
        String format = "RezultateC%d_P%d";
        for (int i = 1; i <= 5; ++i) {
            for (int j = 1; j <= 10; ++j) {
                files.add(String.format(format, i, j));
            }
        }

        int p = 4;
        int p_r = 2;
        int p_w = p - p_r;
        long begin, end;

        if (false) {
            // Sequential
            begin = System.currentTimeMillis();
            computeSequentially();
            printListToFile(sequentialResults, "clasament_sequential");
            end = System.currentTimeMillis();
            System.out.println("Sequential: " + (end - begin));
        } else {
            // Parallel
            begin = System.currentTimeMillis();
            computeParallel(p_r, p_w);
            printListToFile(results.getLinkedList(), "clasament_parallel");
            end = System.currentTimeMillis();
            System.out.println("Parallel: " + (end - begin));
            if (checkCompliance()) {
                System.out.println("All OK!");
            } else {
                System.out.println("Not OK!");
            }
        }
    }

    private static boolean checkCompliance() {
        LinkedList<Node> valid = new LinkedList<>();

        try {
            File obj = new File("src/main/resources/clasament_valid");
            Scanner scanner = new Scanner(obj);

            while (scanner.hasNextLine()) {
                String[] rez = scanner.nextLine().split(",");
                valid.add(new Node(rez[0], Integer.parseInt(rez[1])));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (valid.size() != results.getLinkedList().size()) {
            System.out.printf("ERROR! The valid state contains %d entries, but the actual result contains just %d%n", valid.size(), results.getLinkedList().size());
            return false;
        }

        for (Node node : valid) {
            boolean hasEqual = false;
            for (Node node1 : results.getLinkedList()) {
                if (node.equals(node1)) {
                    hasEqual = true;
                    break;
                }
            }

            if (!hasEqual) {
                System.out.printf("ERROR! Element with id: %s and score: %d expected%n", node.getId(), node.getPoints());
                return false;
            }
        }

        return true;
    }

    private static void computeParallel(int readersNo, int writersNo) {
        readers.set(readersNo);
        Thread[] readers = new Thread[readersNo];
        Thread[] writers = new Thread[writersNo];

        if (readersNo == 1) {
            readers[0] = new Reader(files);
        } else {
            readers[0] = new Reader(files.subList(0, 25));
            readers[1] = new Reader(files.subList(25, 50));
        }

        for (int i = 0; i < writersNo; ++i) {
            Thread thread = new Writer();
            writers[i] = thread;
        }

        Arrays.stream(readers).forEach(Thread::start);
        Arrays.stream(writers).forEach(Thread::start);

        Arrays.stream(readers).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Arrays.stream(writers).forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static void printListToFile(LinkedList<Node> list, String filename) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/" + filename));

            for (Node node : list) {
                bw.write(node.getId() + "," + node.getPoints());
                bw.newLine();
            }
            bw.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class Writer extends Thread {
        @Override
        public void run() {
            while (readers.get() != 0 || !queue.isEmpty()) {
                Node node = queue.pop();
                if (node == null) {
                    continue;
                }
                results.add(node);
            }
            synchronized (queue) {
                queue.notifyAll();
            }
        }
    }

    public static class Reader extends Thread {
        private final List<String> files;

        public Reader(List<String> files) {
            this.files = files;
        }

        @Override
        public void run() {
            for (String file : files) {
                try {
                    File obj = new File("src/main/resources/input_files/" + file);
                    Scanner scanner = new Scanner(obj);

                    while (scanner.hasNextLine()) {
                        String[] result = scanner.nextLine().split(",");

                        queue.push(new Node(result[0], Integer.parseInt(result[1])));
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            readers.decrementAndGet();
        }
    }
}
