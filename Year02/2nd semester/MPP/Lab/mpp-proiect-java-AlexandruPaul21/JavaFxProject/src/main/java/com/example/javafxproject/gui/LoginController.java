package com.example.javafxproject.gui;

import com.example.javafxproject.Main;
import com.example.javafxproject.service.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    public Label addedText;
    public TextField emailText;
    public PasswordField passwordText;

    private Service service;

    public void setService(Service service) {
        this.service = service;
    }

    public void onLoginButtonClick() throws IOException {
        String username = emailText.getText();
        String password = passwordText.getText();
        if (service.validateLogin(username, password)) {
            addedText.setText("Login successful!");

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            MainViewController mainViewController = fxmlLoader.getController();
            mainViewController.setService(service);
            closeWindow();

            stage.setTitle("Main view");
            stage.setScene(scene);
            stage.show();
        } else {
            addedText.setText("Login failed!");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) addedText.getScene().getWindow();
        stage.close();
    }
}
