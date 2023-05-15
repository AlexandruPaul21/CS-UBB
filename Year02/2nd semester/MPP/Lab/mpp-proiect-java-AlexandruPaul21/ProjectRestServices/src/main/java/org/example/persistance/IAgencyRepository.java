package org.example.persistance;

import org.example.model.Agency;

public interface IAgencyRepository extends CrudRepository<String, Agency> {
    boolean existsUser(String username, String password);
}
