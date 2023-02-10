package com.example.simulation2.service;

import com.example.simulation2.domain.Flight;
import com.example.simulation2.repository.FlightDbRepository;
import com.example.simulation2.utils.Observable;

import java.util.Date;
import java.util.List;

public class FlightService extends Observable {
    private FlightDbRepository flightDbRepository;

    public FlightService(FlightDbRepository flightDbRepository) {
        this.flightDbRepository = flightDbRepository;
    }

    public List<String> getAllFrom() {
        return flightDbRepository.getAllFrom();
    }

    public List<String> getAllTo() {
        return flightDbRepository.getAllTo();
    }

    public List<Flight> getFLightsForRoute(String from, String to, Date date) {
        return flightDbRepository.getFLightsForRoute(from, to, date);
    }

    public void book(Long id) {
        flightDbRepository.book(id);
        notifyObs();
    }

}

