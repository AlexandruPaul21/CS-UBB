package org.example;

import org.example.utils.SyncLimitedQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    static int readers = 4;
    static final Lock lock = new ReentrantLock();
    static final AtomicInteger entriesSent = new AtomicInteger(0);
    static AtomicInteger readersLeft = new AtomicInteger(readers);
    static final SyncLimitedQueue queue = new SyncLimitedQueue(readersLeft);
    static final Map<Character, List<Music>> indexedByTitle = new HashMap<>();
    static final Map<Character, List<Music>> indexedByAuthor = new HashMap<>();
    static List<String> filenames = new ArrayList<>();
    
    public static void main(String[] args) throws IOException, InterruptedException {
        //FileGenerator.generateData();
        
        filenames.add("File_0");
        filenames.add("File_1");
        filenames.add("File_2");
        filenames.add("File_3");
        filenames.add("File_4");
        filenames.add("File_5");
        filenames.add("File_6");
        filenames.add("File_7");
        
        List<Reader> threadReaders = new ArrayList<>();
        int report = filenames.size() / readers;
        for (int i = 0; i < readers; ++i) {
            threadReaders.add(new Reader(filenames.subList(report * i, report * (i + 1))));
        }

        List<Writer> threadWriters = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            threadWriters.add(new Writer(lock));
        }
        
        Supervisor supervisor = new Supervisor();
        
        threadReaders.forEach(Thread::start);
        threadWriters.forEach(Thread::start);
        supervisor.start();

        for (Reader threadReader : threadReaders) {
            threadReader.join();
        }

        for (Writer threadWriter : threadWriters) {
            threadWriter.join();
        }
        
        supervisor.join();
        
        indexedByAuthor.clear();
    }
    
    static class Supervisor extends Thread {
        @Override
        public void run() {
            while (readersLeft.get() != 0 || !queue.isEmpty()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {}
                
                synchronized (queue) {
                    int titleChecksum = 0;
                    int authorChecksum = 0;
                    for (Character c : indexedByTitle.keySet()) {
                        titleChecksum += indexedByTitle.get(c).size();
                    }

                    for (Character c : indexedByAuthor.keySet()) {
                        authorChecksum += indexedByAuthor.get(c).size();
                    }

                    if (titleChecksum != entriesSent.get()) {
                        System.out.println("Expected: " + entriesSent.get() + " Found: " + titleChecksum + " ; Indexed by title");
                    } else {
                        System.out.println("All ok - title");
                    }

                    if (authorChecksum != entriesSent.get()) {
                        System.out.println("Expected: " + entriesSent.get() + " Found: " + titleChecksum + " ; Indexed by author");
                    } else {
                        System.out.println("All ok - author");
                    }

                    System.out.println();
                }
            }

            System.out.println("------------------------------------------");
            System.out.println("-------------------FINAL------------------");
            System.out.println("------------------------------------------");

            System.out.println("Found in files: " + entriesSent.get());
            int titleChecksum = 0;
            int authorChecksum = 0;
            for (Character c : indexedByTitle.keySet()) {
                titleChecksum += indexedByTitle.get(c).size();
            }

            for (Character c : indexedByAuthor.keySet()) {
                authorChecksum += indexedByAuthor.get(c).size();
            }

            if (titleChecksum != entriesSent.get()) {
                System.out.println("Expected: " + entriesSent.get() + " Found: " + titleChecksum + " ; Indexed by title");
            } else {
                System.out.println("All ok - title");
            }

            if (authorChecksum != entriesSent.get()) {
                System.out.println("Expected: " + entriesSent.get() + " Found: " + titleChecksum + " ; Indexed by author");
            } else {
                System.out.println("All ok - author");
            }
        }
    }
    
    static class Writer extends Thread {
        private final Lock lock;

        public Writer(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            while (readersLeft.get() != 0 || !queue.isEmpty()) {
                Music music = null;
                try {
                    music = queue.pop();
                } catch (InterruptedException ignored) {}
                
                if (music == null) {
                    queue.finish();
                    continue;
                }
                
                lock.lock();
                List<Music> actual = indexedByTitle.get(music.name.charAt(0));
                if (actual == null) {
                    actual = new ArrayList<>();
                }
                actual.add(music);

                indexedByTitle.put(music.name.charAt(0), actual);

                actual = indexedByAuthor.get(music.author.charAt(0));
                if (actual == null) {
                    actual = new ArrayList<>();
                }
                actual.add(music);

                indexedByAuthor.put(music.author.charAt(0), actual);
                lock.unlock();
            }
        }
    }
    
    static class Reader extends Thread {
        List<String> filenames;

        public Reader(List<String> filenames) {
            this.filenames = filenames;
        }

        @Override
        public void run() {
            for (String file : filenames) {
                try {
                    File obj = new File("src/main/resources/" + file);
                    Scanner scanner = new Scanner(obj);
                    
                    while (scanner.hasNextLine()) {
                        String[] elems = scanner.nextLine().split(",");
                        
                        queue.push(new Music(
                                elems[0],
                                elems[1],
                                elems[2]
                        ));
                        entriesSent.incrementAndGet();
                    }
                } catch (FileNotFoundException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (readersLeft.decrementAndGet() == 0) {
                queue.finish();
            }
        }
    }
}
