package com.wms.wmsproject.model.dtos;

import com.wms.wmsproject.model.Task;

import java.time.LocalDateTime;

public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String deadline;

    public TaskDto(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.deadline = task.getDeadline().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
