package org.example.repository;

import org.example.model.Entity;

public interface CrudRepository<ID, E extends Entity<ID>> {
    E findOne(ID id);
    Iterable<E> findAll();
    E save(E entity);
}
