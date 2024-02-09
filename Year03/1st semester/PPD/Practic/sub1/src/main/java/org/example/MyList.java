package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyList {
    private final Lock lock = new ReentrantLock();
    private final List<Student> students = new ArrayList<>();

    public MyList() {
    }
    
    public void add(Student student) {
        lock.lock();
        students.add(student);
        lock.unlock();
    }
    
    public List<Student> getStudents() {
        try {
            lock.lock();
            return students;
        } finally {
            lock.unlock();
        }
    }

    public void printPrettyUnder5() {
        lock.lock();
        students.stream()
                .filter((student) -> student.mean < 5)
                .forEach(System.out::println);
        lock.unlock();
    }
    
    public void printPretty() {
        lock.lock();
        students.forEach(System.out::println);
        lock.unlock();
    }
}
