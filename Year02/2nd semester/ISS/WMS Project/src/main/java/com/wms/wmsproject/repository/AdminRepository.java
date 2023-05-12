package com.wms.wmsproject.repository;

import com.wms.wmsproject.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    @Query("SELECT a FROM Admin a WHERE a.id = ?1 AND a.password = ?2")
    Admin findByIdAndPassword(String username, String password);
}
