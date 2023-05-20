package com.wms.wmsproject.controller;

import com.wms.wmsproject.model.Admin;
import com.wms.wmsproject.model.Worker;
import com.wms.wmsproject.model.dtos.TaskDto;
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
    public TableView<TaskDto> taskTable;
    public TableColumn<TaskDto, String> taskColumn;
    public TableColumn<TaskDto, String> workerColumn;
    public TableColumn<TaskDto, String> deadlineColumn;

    ObservableList<WorkerDto> workers = FXCollections.observableArrayList();
    ObservableList<TaskDto> tasks = FXCollections.observableArrayList();

    public Service service;

    @Override
    public void update() {
        populateTasksTable();
        populateWorkersTable();
    }

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        initializeTables();
        populateWorkersTable();
        populateTasksTable();
        (workersTable.getScene().getWindow()).focusedProperty().addListener((observable, oldValue, newValue) -> populateWorkersTable());
    }

    private void populateTasksTable() {
        tasks.clear();
        tasks.setAll(service.getAllTasks()
                .stream()
                .map(TaskDto::new)
                .toList());
    }

    private void populateWorkersTable() {
        workers.clear();
        workers.setAll(service.getAllWorkers()
                .stream()
                .map(WorkerDto::new)
                .toList());
    }

    private void initializeTables() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        startedWorkingColumn.setCellValueFactory(new PropertyValueFactory<>("startedWorking"));
        workersTable.setItems(workers);

        taskColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        workerColumn.setCellValueFactory(new PropertyValueFactory<>("worker"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        taskTable.setItems(tasks);
    }

    public TableView<WorkerDto> workersTable;
    public TableColumn<WorkerDto, String> nameColumn;
    public TableColumn<WorkerDto, String> startedWorkingColumn;

    public Admin loggedAdmin;

    public void createWorkerClicked() throws IOException {
        Stage stage = new Stage();
        stage.setTitle("Create worker");
        Controller ctrl = Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/worker-details.fxml");
        ctrl.setService(service);
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
        Controller ctrl = Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/worker-details.fxml");
        ((WorkerCrudController) ctrl).setWorker(new Worker(selectedWorker.getId(), selectedWorker.getName(), selectedWorker.getPassword()));
        ctrl.setService(service);
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

    public void seeAllAvailableWorkersClicked() {
        workers.clear();
        workers.setAll(service.getAllAvailableWorkers()
                .stream()
                .map(WorkerDto::new)
                .toList());
    }

    public void assignTaskClicked() throws IOException {
        WorkerDto selectedWorker = workersTable.getSelectionModel().getSelectedItem();

        if (selectedWorker == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No worker selected");
            alert.setContentText("Please select a worker to assign the task");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Assign task");
        Controller ctrl = Functions.fxmlLoad(stage, "/com/wms/wmsproject/gui/task-details.fxml");
        ctrl.setService(service);
        ((TaskCrudController) ctrl).setLoggedWorker(new Worker(selectedWorker.getId(), selectedWorker.getName(), selectedWorker.getPassword()));
    }

    public void logoutButtonClicked() {
        service.removeObserver(this);
        Stage stage = (Stage) workersTable.getScene().getWindow();
        stage.close();
    }

    public void deleteTasksClicked() {
        TaskDto selectedTask = taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No task selected");
            alert.setContentText("Please select a task to delete");
            alert.showAndWait();
            return;
        }

        service.markAsDone(selectedTask.getId());
        populateTasksTable();
    }
}
