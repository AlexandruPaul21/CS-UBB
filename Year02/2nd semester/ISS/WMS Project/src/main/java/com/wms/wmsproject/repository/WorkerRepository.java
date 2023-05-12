package com.wms.wmsproject.repository;

import com.wms.wmsproject.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, String> {
    @Query("SELECT w FROM Worker w WHERE w.id = ?1 AND w.password = ?2")
    Worker findByIdAndPassword(String username, String password);
}
