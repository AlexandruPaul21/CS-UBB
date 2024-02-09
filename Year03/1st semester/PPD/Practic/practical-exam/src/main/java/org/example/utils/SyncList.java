package org.example.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SyncList<T> {
    private final List<T> elements = new ArrayList<>();
    
    public void add(T elem) {
        synchronized (elements) {
            elements.add(elem);
        }
    }
    
    public Stream<T> stream() {
        synchronized (elements) {
            return elements.stream();
        }
    }
    
    public void remove(int index) {
        synchronized (elements) {
            elements.remove(index);
        }
    }
    
    public void remove(T element) {
        synchronized (elements) {
            elements.remove(element);
        }
    }
    
    public int size() {
        synchronized (elements) {
            return elements.size();
        }
    }
}
