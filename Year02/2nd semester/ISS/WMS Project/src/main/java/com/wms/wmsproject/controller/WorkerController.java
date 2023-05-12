package com.wms.wmsproject.controller;

import com.wms.wmsproject.model.Worker;

public class WorkerController implements Controller {
    private Worker loggedWorker;

    public void setLoggedWorker(Worker loggedWorker) {
        this.loggedWorker = loggedWorker;
    }
}
