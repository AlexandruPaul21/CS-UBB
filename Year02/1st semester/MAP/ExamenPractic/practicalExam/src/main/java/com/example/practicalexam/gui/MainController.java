package com.example.practicalexam.gui;

import com.example.practicalexam.HelloApplication;
import com.example.practicalexam.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private Service service;

    public void openNewWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        TableController tableController = fxmlLoader.getController();
        tableController.setService(service);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void setService(Service service) {
        this.service = service;
    }
}