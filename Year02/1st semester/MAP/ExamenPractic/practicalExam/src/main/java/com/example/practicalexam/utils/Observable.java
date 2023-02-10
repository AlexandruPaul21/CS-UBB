package com.example.practicalexam.utils;

import com.example.practicalexam.domain.City;
import com.example.practicalexam.domain.DTOs.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Observable {
    private List<Observer> observers;
    private List<View> views = new ArrayList<>();

    public Observable() {
        observers = new ArrayList<>();
    }

    public List<View> getViews() {
        return views;
    }

    public void change(City c1, City c2) {
        for (View view : views) {
            if (view.getCity1().getId().equals(c1.getId()) && view.getCity2().getId().equals(c2.getId())) {
                if (view.app > 0) {
                    view.app--;
                } else {
                    views.remove(view);
                    break;
                }
            }
        }
        for (Observer obs : observers) {
            obs.update(c1, c2);
        }
    }

    public void notifyObs(City c1, City c2) {
        boolean found = false;
        for (View view : views) {
            if (view.getCity1().getId().equals(c1.getId()) && view.getCity2().getId().equals(c2.getId())) {
                view.app += 1;
                found = true;
            }
        }
        if (!found) {
            View view = new View(c1, c2);
            views.add(view);
        }
        for (Observer obs : observers) {
            obs.update(c1, c2);
        }
    }

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObservers(Observer obs) {
        observers.remove(obs);
    }
}
