package org.example.persistance;


import org.example.model.Client;

public interface IClientRepository extends CrudRepository<Long, Client> {
    Long getLowestAvbId();
}
