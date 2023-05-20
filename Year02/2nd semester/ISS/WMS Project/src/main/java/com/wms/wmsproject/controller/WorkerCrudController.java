package com.wms.wmsproject.controller;

import com.wms.wmsproject.model.Worker;
import com.wms.wmsproject.service.Service;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WorkerCrudController implements Controller {
    private Worker selectedWorker;

    public void setWorker(Worker selectedWorker) {
        this.selectedWorker = selectedWorker;
    }

    private Service service;

    public void setService(Service service) {
        this.service = service;
        service.addObserver(this);
        if (selectedWorker != null) {
            nameTextField.setText(selectedWorker.getName());
            usernameTextField.setText(selectedWorker.getId());
            usernameTextField.setDisable(true);
            passwordTextField.setText(selectedWorker.getPassword());
        }
    }

    @Override
    public void update() {}

    public TextField nameTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;

    public void cancelButtonClicked() {
        Stage stage = (Stage) nameTextField.getScene().getWindow();
        stage.close();
    }

    public void submitButtonClicked() {
        String name = nameTextField.getText();
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if (name.equals("") || username.equals("") || password.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Some fields are empty");
            alert.show();
            return;
        }

        if (password.length() < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password is too short");
            alert.setContentText("Please insert a longer password");
            alert.show();
            return;
        }

        Worker worker = new Worker(username, name, password);

        if (selectedWorker != null) {
            service.updateWorker(worker);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Worker updated successfully");
            alert.show();
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
            return;
        }

        worker.setStartedWorking(null);
        if (service.addWorker(worker) != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Worker added successfully");
            alert.show();
            service.removeObserver(this);
            Stage stage = (Stage) nameTextField.getScene().getWindow();
            stage.close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Something went wrong! Try again");
            alert.show();
        }
    }
}
