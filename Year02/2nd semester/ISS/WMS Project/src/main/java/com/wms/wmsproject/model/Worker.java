package com.wms.wmsproject.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "workers")
public class Worker extends Employee {
    @OneToMany(mappedBy = "worker")
    private List<Task> tasks;

    @Nullable
    private LocalDateTime startedWorking;

    public Worker() {
    }

    public Worker(String username, String name, String password) {
        super(username, password, name);
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public LocalDateTime getStartedWorking() {
        return startedWorking;
    }

    public void setStartedWorking(LocalDateTime startedWorking) {
        this.startedWorking = startedWorking;
    }
}
