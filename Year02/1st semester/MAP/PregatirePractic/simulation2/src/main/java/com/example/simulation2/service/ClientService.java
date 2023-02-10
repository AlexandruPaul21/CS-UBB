package com.example.simulation2.service;

import com.example.simulation2.domain.Client;
import com.example.simulation2.repository.ClientDbRepository;

public class ClientService {
    private final ClientDbRepository clientDbRepository;

    public ClientService(ClientDbRepository clientDbRepository) {
        this.clientDbRepository = clientDbRepository;
    }

    public Client findOne(String username) {
        return clientDbRepository.findOne(username);
    }
}
