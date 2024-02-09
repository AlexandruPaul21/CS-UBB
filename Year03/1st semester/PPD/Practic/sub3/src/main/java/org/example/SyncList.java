package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncList {
    private final Lock lock = new ReentrantLock();
    private final List<Integer> elements = new ArrayList<>();

    public SyncList() {
    }
    
    public void add(Integer elem) {
        lock.lock();
        elements.add(elem);
        lock.unlock();
    }
}
