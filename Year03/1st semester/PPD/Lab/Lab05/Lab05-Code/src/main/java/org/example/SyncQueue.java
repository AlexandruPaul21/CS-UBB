package org.example;

import org.example.domain.Participant;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncQueue {
    private final int MAX = 100;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final AtomicInteger readersLeft;

    public SyncQueue(AtomicInteger readersLeft) {
        this.readersLeft = readersLeft;
    }

    public int first = 0;
    public int last = 0;
    public int count = 0;
    private final Participant[] queue = new Participant[101];

    public void finish() {
        lock.lock();
        notEmpty.signal();
        notFull.signal();
        lock.unlock();
    }

    public void push(Participant node) throws InterruptedException {
        lock.lock();
        try {
            while (count == MAX) {
                notFull.await();
            }
            queue[last++] = node;
            if (last == MAX) {
                last = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Participant pop() throws InterruptedException {
        Participant result;
        lock.lock();
        try {
            while (count == 0) {
                if (readersLeft.get() == 0) {
                    return null;
                }
                notEmpty.await();
            }
            result = queue[first++];

            if (first == MAX) {
                first = 0;
            }
            --count;
            notFull.signal();
            return result;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        return count == 0;
    }
}
