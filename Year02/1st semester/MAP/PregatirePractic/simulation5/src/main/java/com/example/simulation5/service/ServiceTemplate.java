package com.example.simulation5.service;

import com.example.simulation5.repository.DbRepositoryTemplate;

public class ServiceTemplate {
    private final DbRepositoryTemplate repository;

    public ServiceTemplate(DbRepositoryTemplate repository) {
        this.repository = repository;
    }
}
