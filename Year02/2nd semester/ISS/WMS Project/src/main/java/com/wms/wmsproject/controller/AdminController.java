package com.wms.wmsproject.controller;

import com.wms.wmsproject.model.Admin;
import com.wms.wmsproject.model.Worker;
import com.wms.wmsproject.model.dtos.WorkerDto;
import com.wms.wmsproject.service.Service;
import com.wms.wmsproject.utils.functions.Functions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController implements Controller {
    ObservableList<WorkerDto> workers = FXCollections.observableArrayList();

    public Service service;

    public void setService(Service service) {
        this.service = service;
        initializeWorkersTable();
        populateWorkersTable();
        (workersTable.getScene().getWindow()).focusedProperty().addListener((observable, oldValue, newValue) -> populateWorkersTable());
    }

    private void populateWorkersTable() {
        workers.clear();
        workers.setAll(service.getAllWorkers()
                .stream()
                .map(WorkerDto::new)
                .toList());
    }

    private void initializeWorkersTable() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startedWorkingColumn.setCellValueFactory(new PropertyValueFactory<>("startedWorking"));
        workersTable.setItems(workers);
    }

    public TableView<WorkerDto> workersTable;
    public TableColumn<WorkerDto, String> nameColumn;
    public TableColumn<WorkerDto, String> startedWorkingColumn;

    public Admin loggedAdmin;

    public void createWorkerClicked() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Create worker");
        Controller ctrl = Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/worker-detalies.fxml");
        ((WorkerCrudController) ctrl).setService(service);
    }

    public void updateWorkerClicked() throws IOException {
        WorkerDto selectedWorker = workersTable.getSelectionModel().getSelectedItem();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No worker selected");
            alert.setContentText("Please select a worker to update");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Update worker");
        Controller ctrl = Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/worker-detalies.fxml");
        ((WorkerCrudController) ctrl).setWorker(new Worker(selectedWorker.getId(), selectedWorker.getName(), selectedWorker.getPassword()));
        ((WorkerCrudController) ctrl).setService(service);
    }

    public void deleteWorkerClicked() {
        WorkerDto selectedWorker = workersTable.getSelectionModel().getSelectedItem();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No worker selected");
            alert.setContentText("Please select a worker to update");
            alert.showAndWait();
            return;
        }

        service.deleteWorker(selectedWorker.getId());
        populateWorkersTable();
    }

    public void seeAllWorkersClicked() {
        populateWorkersTable();
    }
}
