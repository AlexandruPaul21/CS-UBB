package com.example.simularemap.service;

import com.example.simularemap.domain.Client;
import com.example.simularemap.domain.Dtos.OfferDTO;
import com.example.simularemap.repository.dbRepo.ClientsDbRepo;

import java.util.List;

public class ClientsService {
    private ClientsDbRepo clientsDbRepo;

    public ClientsService(ClientsDbRepo clientsDbRepo) {
        this.clientsDbRepo = clientsDbRepo;
    }

    public List<OfferDTO> findByPercentage(int percentage) {
        return clientsDbRepo.findByPercentage(percentage);
    }

    public Client findById(Long id) {
        return clientsDbRepo.findOne(id);
    }
}
