package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class MyQueue {
    public int first = 0;
    public int last = -1;
    private final AtomicInteger value;
    private final Node[] queue = new Node[10000];

    public MyQueue(AtomicInteger value) {
        this.value = value;
    }

    public synchronized void push(Node node) {
        queue[++last] = node;
        notifyAll();
    }

    public synchronized Node pop() {
        while (first > last) {
            if (value.get() == 0) {
                return null;
            }
            try {
                wait();
            } catch (InterruptedException ignored) {}
        }
        return queue[first++];
    }

    public boolean isEmpty() {
        return first > last;
    }
}
