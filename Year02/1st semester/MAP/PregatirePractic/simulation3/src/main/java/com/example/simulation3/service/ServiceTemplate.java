package com.example.simulation3.service;

import com.example.simulation3.repository.DbRepositoryTemplate;

public class ServiceTemplate {
    private final DbRepositoryTemplate repository;

    public ServiceTemplate(DbRepositoryTemplate repository) {
        this.repository = repository;
    }
}
