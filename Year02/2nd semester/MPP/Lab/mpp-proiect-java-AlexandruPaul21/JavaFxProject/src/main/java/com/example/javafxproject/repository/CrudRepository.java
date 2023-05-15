package com.example.javafxproject.repository;


import com.example.javafxproject.model.Entity;

public interface CrudRepository<ID, E extends Entity<ID>> {
    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity);
}
