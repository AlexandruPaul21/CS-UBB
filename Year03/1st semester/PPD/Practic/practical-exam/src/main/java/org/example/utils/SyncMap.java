package org.example.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SyncMap<T, S> {
    private final Map<T, S> map = new HashMap<>();
 
    public Set<T> keySet() {
        synchronized (map) {
            return map.keySet();
        }
    }
    
    public S get(T key) {
        synchronized (map) {
            return map.get(key);
        }
    }
    
    public void put(T key, S value) {
        synchronized (map) {
            map.put(key, value);
        }
    }
}
