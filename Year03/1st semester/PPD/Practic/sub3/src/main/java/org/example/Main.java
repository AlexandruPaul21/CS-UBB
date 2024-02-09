package org.example;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static int readersNumber = 5;
    static int specialistNumber = 6;
    static AtomicInteger readersLeft = new AtomicInteger(readersNumber);
    static final List<SyncLimitedQueue> queues = new ArrayList<>();
    static final SyncMap<String, List<Incident>> solvedByAgent = new SyncMap<>();
    static Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        queues.add(new SyncLimitedQueue(readersLeft));
        queues.add(new SyncLimitedQueue(readersLeft));
        queues.add(new SyncLimitedQueue(readersLeft));
        
        List<Reader> readers = new ArrayList<>();
        for (int i = 0; i < readersNumber; ++ i) {
            readers.add(new Reader("id_" + i));
        }
        
        List<Writer> writers = new ArrayList<>();
        for (int i = 0; i < specialistNumber; ++i) {
            writers.add(new Writer(i / 2 + 1));
        }
        
        Supervisor supervisor = new Supervisor();
        
        readers.forEach(Thread::start);
        writers.forEach(Thread::start);
        supervisor.start();

        for (Reader reader : readers) {
            reader.join();
        }

        for (Writer writer : writers) {
            writer.join();
        }
        
        supervisor.join();
    }
    
    static class Supervisor extends Thread {
        @Override
        public void run() {
            while (readersLeft.get() != 0 || queues.stream().anyMatch(queue -> !queue.isEmpty())) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException ignored) {}
                
                synchronized (solvedByAgent) {
                    synchronized (queues) {
                        System.out.println("Report at: " + Timestamp.valueOf(LocalDateTime.now()));
                        System.out.println("Level 1: " + queues.get(0).count);
                        System.out.println("Level 2: " + queues.get(1).count);
                        System.out.println("Level 3: " + queues.get(2).count);
                        System.out.println("---------Solved per agent------");
                        for (String key : solvedByAgent.keySet()) {
                            System.out.println(key + ": " + solvedByAgent.get(key).size());
                        }
                        System.out.println();
                    }
                }
            }

            System.out.println();
            System.out.println("Final report at: " + Timestamp.valueOf(LocalDateTime.now()));
            for (String key : solvedByAgent.keySet()) {
                System.out.println(key + ": " + solvedByAgent.get(key).size());
            }
        }
    }
    
    static class Writer extends Thread {
        Integer level;

        public Writer(Integer level) {
            this.level = level;
        }

        @Override
        public void run() {
            while (readersLeft.get() != 0 || !queues.get(level - 1).isEmpty()) {
                Incident incident = null;
                try {
                    incident = queues.get(level - 1).pop();
                } catch (InterruptedException ignored) {}

                if (incident == null) {
                    queues.get(level - 1).finish();
                    continue;
                }
                
                try {
                    Thread.sleep(5);
                } catch (InterruptedException ignored) {}

                synchronized (solvedByAgent) {
                    List<Incident> incidents = solvedByAgent.get(incident.idAgent);
                    
                    if (incidents == null) {
                        incidents = new ArrayList<>();
                    }
                    
                    incidents.add(incident);
                    
                    solvedByAgent.put(incident.idAgent, incidents);
                }
            }
        }
    }
    
    static class Reader extends Thread {
        private final String idAgent;

        public Reader(String idAgent) {
            this.idAgent = idAgent;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; ++i) {
                int level = Math.abs(random.nextInt()) % 3 + 1;
                try {
                    Thread.sleep(10);
                    queues.get(level - 1).push(new Incident(idAgent, String.valueOf(Math.abs(random.nextInt()) % 1000), level));
                } catch (InterruptedException ignored) {}
            }
            
            if (readersLeft.decrementAndGet() == 0) {
                queues.forEach(SyncLimitedQueue::finish);
            }
        }
    }
}
