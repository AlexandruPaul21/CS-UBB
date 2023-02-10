package com.example.simulation4.service;

import com.example.simulation4.repository.DbRepositoryTemplate;

public class ServiceTemplate {
    private final DbRepositoryTemplate repository;

    public ServiceTemplate(DbRepositoryTemplate repository) {
        this.repository = repository;
    }
}
