package com.example.javafxproject;

import com.example.javafxproject.gui.LoginController;
import com.example.javafxproject.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("AgencyConfiguration.xml");

        Service service = context.getBean(Service.class);

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}