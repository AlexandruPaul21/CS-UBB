package com.example.simularemap.utils;

import java.util.ArrayList;
import java.util.List;

public class Observable {
    private List<Observer> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public void notifyObs() {
        for (Observer obs : observers) {
            obs.update();
        }
    }

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObservers(Observer obs) {
        observers.remove(obs);
    }
}
