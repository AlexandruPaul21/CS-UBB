package com.example.javafxproject.repository;


import com.example.javafxproject.model.Client;

public interface IClientRepository extends CrudRepository<Long, Client> {
    Long getLowestAvbId();
}
