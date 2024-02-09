package org.example;

import org.example.utils.SyncLimitedQueue;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static BufferedWriter bw;
    static int t_oft = 20;
    static int t_r = 10;
    static int t_ort = 40;
    static int t_orl = 25;
    static int producersNumber = 2;
    static AtomicInteger producersLeft = new AtomicInteger(producersNumber);
    
    static int consumersNumber = 3;
    static AtomicInteger consumersLeft = new AtomicInteger(consumersNumber);
    static final SyncLimitedQueue firstQueue = new SyncLimitedQueue(producersLeft);
    static final List<SyncLimitedQueue> serviceQueue = new ArrayList<>();
    
    static final Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException, IOException {
        bw = new BufferedWriter(new FileWriter("src/main/resources/consults.txt"));
        serviceQueue.add(new SyncLimitedQueue(consumersLeft));
        serviceQueue.add(new SyncLimitedQueue(consumersLeft));
        serviceQueue.add(new SyncLimitedQueue(consumersLeft));
        serviceQueue.add(new SyncLimitedQueue(consumersLeft));
        
        List<Producer> producers = new ArrayList<>();
        for (int i = 0; i < producersNumber; ++i) {
            producers.add(new Producer());
        }
        
        List<Consumer> consumers = new ArrayList<>();
        for (int i = 0; i < consumersNumber; ++i) {
            consumers.add(new Consumer());
        }
        
        List<Consulter> consulters = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            consulters.add(new Consulter(i + 1));
        }
        
        Supervisor supervisor = new Supervisor();
        
        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);
        consulters.forEach(Thread::start);
        supervisor.start();
        
        for (Producer producer : producers) {
            producer.join();
        }

        for (Consumer consumer : consumers) {
            consumer.join();
        }
        
        for (Consulter consulter : consulters) {
            consulter.join();
        }
        
        supervisor.join();
    }
    
    static class Supervisor extends Thread {
        BufferedWriter bufferedWriter;

        public Supervisor() throws IOException {
            bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/log.txt"));
        }

        @Override
        public void run() {
            while (consumersLeft.get() != 0 || serviceQueue.stream().anyMatch(queue -> !queue.isEmpty())) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}

                synchronized (serviceQueue) {
                    synchronized (firstQueue) {
                        try {
                            bufferedWriter.write(Timestamp.valueOf(LocalDateTime.now()) + ",");
                            bufferedWriter.write(serviceQueue.get(0).count + ",");
                            bufferedWriter.write(serviceQueue.get(1).count + ",");
                            bufferedWriter.write(serviceQueue.get(2).count + ",");
                            bufferedWriter.write(serviceQueue.get(3).count + ",");
                            bufferedWriter.write(String.valueOf(firstQueue.count));
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        } catch (IOException ignored) {}
                    }
                }
            }
        }
    }

    static class Consulter extends Thread {
        private final int level;
        private int timeout;

        public Consulter(int level) {
            this.level = level;
            switch (level) {
                case 1 -> timeout = t_oft;
                case 2 -> timeout = t_r;
                case 3 -> timeout = t_ort;
                case 4 -> timeout = t_orl;
            }
        }

        @Override
        public void run() {
            while (consumersLeft.get() != 0 || serviceQueue.stream().anyMatch(queue -> !queue.isEmpty())) {
                Consult consult = null;
                try {
                    consult = serviceQueue.get(level - 1).pop();
                } catch (InterruptedException ignored) {}

                if (consult == null) {
                    serviceQueue.get(level - 1).finish();
                    continue;
                }

                try {
                    Thread.sleep(timeout);
                } catch (InterruptedException ignored) {}

                writeToFile(consult.idConsult + "," + level + "," + timeout);
                
                if (consult.service2 != 0) {
                    consult.service1 = consult.service2;
                    consult.service2 = 0;
                    try {
                        serviceQueue.get(consult.service1 - 1).push(consult);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }

    private static void writeToFile(String s) {
        synchronized (bw) {
            try {
                bw.write(s);
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer extends Thread {

        @Override
        public void run() {
            while (producersLeft.get() != 0 || !firstQueue.isEmpty()) {
                Consult consult = null;
                try {
                    consult = firstQueue.pop();
                } catch (InterruptedException ignored) {}
                
                if (consult == null) {
                    firstQueue.finish();
                    continue;
                }

                try {
                    serviceQueue.get(consult.service1 - 1).push(consult);
                } catch (InterruptedException ignored) {}
            }

            if (consumersLeft.decrementAndGet() == 0) {
                serviceQueue.forEach(SyncLimitedQueue::finish);
            }
        }
    }
    
    static private int getRandomService(int mod) {
        return Math.abs(random.nextInt()) % mod;
    }

    static class Producer extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 100; ++i) {
                try {
                    firstQueue.push(
                            new Consult(
                                    String.valueOf(Math.abs(random.nextInt()) % 1000), 
                                    getRandomService(4) + 1, 
                                    getRandomService(5)
                            )
                    );
                } catch (InterruptedException ignored) {}
            }
            
            if (producersLeft.decrementAndGet() == 0) {
                firstQueue.finish();
            }
        }
    }
}
