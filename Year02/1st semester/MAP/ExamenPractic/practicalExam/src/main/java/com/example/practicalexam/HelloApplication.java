package com.example.practicalexam;

import com.example.practicalexam.gui.MainController;
import com.example.practicalexam.repository.CitiesDbRepository;
import com.example.practicalexam.repository.TsDbRepository;
import com.example.practicalexam.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.Serializable;

public class HelloApplication extends Application {
    private static String[] sysArgs;
    private Service service;

    private void setup() {
        String urlDb = "jdbc:postgresql://localhost:5432/Examen";
        String usernameDb = "postgres";
        String passDb = "1234";
        CitiesDbRepository citiesDbRepository = new CitiesDbRepository(urlDb, usernameDb, passDb);
        TsDbRepository tsDbRepository = new TsDbRepository(urlDb, usernameDb, passDb);
        service = new Service(citiesDbRepository, tsDbRepository);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setup();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController mainController = fxmlLoader.getController();
        mainController.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        sysArgs = args;
        launch();
    }
}