package com.example.simulation4.service;

import com.example.simulation4.domain.Pacient;
import com.example.simulation4.repository.PacientDbRepository;

import java.util.List;

public class PacientService {
    private PacientDbRepository pacientDbRepository;

    public PacientService(PacientDbRepository pacientDbRepository) {
        this.pacientDbRepository = pacientDbRepository;
    }

    public List<Pacient> findAllNeed() {
        return pacientDbRepository.findInNeed();
    }
}
