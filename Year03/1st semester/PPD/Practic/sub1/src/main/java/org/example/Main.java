package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static int totalWorker = 5;
    public static AtomicInteger workersLeft = new AtomicInteger(totalWorker);
    public static List<Student> existingStudents = new ArrayList<>();
    public static MyList addedStudents = new MyList();
    public static Lock lock = new ReentrantLock();
    public static Condition condition = lock.newCondition();
    public static Random random = new Random();
    
    public static void main(String[] args) throws InterruptedException {
        generateStudents(100);
        
        Manager manager = new Manager();
        manager.start();
        
        List<Worker> workers = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            workers.add(new Worker(existingStudents.subList(20 * i, 20 * (i + 1))));
        }
        
        workers.forEach(Thread::start);

        for (Worker worker : workers) {
            worker.join();
        }
        
        manager.join();
    }
    
    private static Float randomMean() {
        int points = Math.abs(random.nextInt()) % 101;
        
        return (float) points / 10;
    }
    
    private static void generateStudents(int number) {
        for (long i = 0; i < number; ++i) {
            existingStudents.add(new Student(i, randomMean()));
        }
    }
    
    static class Worker extends Thread {
        List<Student> students;

        public Worker(List<Student> students) {
            this.students = students;
        }

        @Override
        public void run() {
            for (Student student : students) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {}
                addedStudents.add(student);
                if (student.mean < 5) {
                    lock.lock();
                    condition.signal();
                    lock.unlock();
                }
            }
            
            workersLeft.decrementAndGet();
        }
    }
    
    static class Manager extends Thread {
        
        @Override
        public void run() {
            while (workersLeft.get() != 0) {
                try {
                    lock.lock();
                    condition.await();
                } catch (InterruptedException ignored) {}
                finally {
                    lock.unlock();
                }
                
                addedStudents.printPrettyUnder5();
                System.out.println("---------------------------------------------------------");
                System.out.println();
            }

            System.out.println("---------------------------------------------------------");
            System.out.println("---------------------FINAL RESULTS-----------------------");
            System.out.println("---------------------------------------------------------");
            
            addedStudents.printPretty();
        }
    }
}
