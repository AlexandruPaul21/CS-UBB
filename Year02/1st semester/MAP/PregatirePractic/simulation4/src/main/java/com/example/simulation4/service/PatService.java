package com.example.simulation4.service;

import com.example.simulation4.repository.PatDbRepository;
import com.example.simulation4.utils.Observable;

public class PatService extends Observable {
    private PatDbRepository patDbRepository;

    public PatService(PatDbRepository patDbRepository) {
        this.patDbRepository = patDbRepository;
    }

    public boolean interneaza(Long cnp, String tip) {
        return patDbRepository.interneaza(cnp, tip);
    }

    public int getNrOc() {
        return patDbRepository.getNrOfocupat();
    }

    public int getNrFree(String type) {
        return patDbRepository.getNrOfFree(type);
    }
}
