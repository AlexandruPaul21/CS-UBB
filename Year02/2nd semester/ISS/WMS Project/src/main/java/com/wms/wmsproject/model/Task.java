package com.wms.wmsproject.model;

import com.wms.wmsproject.utils.enums.TaskType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
public class Task extends Identifiable<Long> {
    private String name;
    private String description;
    private LocalDateTime deadline;

    @Enumerated(EnumType.ORDINAL)
    private TaskType taskType;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    private Worker worker;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Task() {}

    public Task(Long id, String name, String description, LocalDateTime deadline, TaskType taskType) {
        super(id);
        this.name = name;
        this.description = description;
        this.deadline = deadline;
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
