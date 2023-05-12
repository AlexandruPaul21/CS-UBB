package com.wms.wmsproject.model.dtos;

import com.wms.wmsproject.model.Worker;

public class WorkerDto {
    private String id;
    private String name;
    private String startedWorking;
    private String password;

    public WorkerDto(Worker worker) {
        this.id = worker.getId();
        this.name = worker.getName();
        this.startedWorking = "";
        if (worker.getStartedWorking() != null) {
            this.startedWorking = worker.getStartedWorking().toString();
        }
        this.password = worker.getPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartedWorking() {
        return startedWorking;
    }

    public void setStartedWorking(String startedWorking) {
        this.startedWorking = startedWorking;
    }
}
