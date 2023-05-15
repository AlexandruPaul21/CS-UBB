package org.example.persistance;

import org.example.Client;

public interface IClientRepository extends CrudRepository<Long, Client> {
    Long getLowestAvbId();
}
