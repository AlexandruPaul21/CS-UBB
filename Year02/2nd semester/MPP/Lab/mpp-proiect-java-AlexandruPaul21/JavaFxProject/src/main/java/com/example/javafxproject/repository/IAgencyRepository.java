package com.example.javafxproject.repository;


import com.example.javafxproject.model.Agency;

public interface IAgencyRepository extends CrudRepository<String, Agency> {
    boolean existsUser(String username, String password);
}
