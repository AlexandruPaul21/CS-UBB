package com.wms.wmsproject.repository;

import com.wms.wmsproject.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, String> {
    @Query("SELECT w FROM Worker w WHERE w.id = ?1 AND w.password = ?2")
    Worker findByIdAndPassword(String username, String password);

    @Modifying
    @Query("UPDATE Worker w SET w.startedWorking = CURRENT_TIMESTAMP WHERE w.id = ?1")
    void startWorking(String id);

    @Modifying
    @Query("UPDATE Worker w SET w.startedWorking = NULL WHERE w.id = ?1")
    void stopWorking(String id);

    @Query("SELECT w FROM Worker w WHERE w.startedWorking IS NOT NULL")
    List<Worker> findAllAvailableWorkers();
}
