package org.example.domain;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node {
    public final Lock lock = new ReentrantLock();
    private Participant data;
    public Node next;
    public Node previous;

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }

    public Node(Participant data, Node next, Node previous) {
        this.data = data;
        this.next = next;
        this.previous = previous;
    }

    public Participant getData() {
        return data;
    }

    public void setData(Participant data) {
        this.data = data;
    }

    public boolean isNotLastNode() {
        return data != null;
    }
}
