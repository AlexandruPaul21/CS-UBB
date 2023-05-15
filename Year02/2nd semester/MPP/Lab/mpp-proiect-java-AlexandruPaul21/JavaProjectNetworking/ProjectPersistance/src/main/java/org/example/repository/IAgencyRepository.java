package org.example.repository;

import org.example.Agency;

public interface IAgencyRepository extends CrudRepository<String, Agency> {
    boolean existsUser(String username, String password);
}
