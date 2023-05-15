package org.example.repository;

import org.example.Client;

public interface IClientRepository extends CrudRepository<Long, Client> {
    Long getLowestAvbId();
}
