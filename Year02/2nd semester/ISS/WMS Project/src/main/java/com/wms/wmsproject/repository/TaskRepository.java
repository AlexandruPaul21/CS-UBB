package com.wms.wmsproject.repository;

import com.wms.wmsproject.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    @Query("SELECT t FROM Task t WHERE t.worker.id = ?1")
    List<Task> findAllByWorkerId(String workerId);

    @Modifying
    @Query("DELETE FROM Task t WHERE t.id = ?1")
    void markAsDone(Long id);
}
