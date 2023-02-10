package com.example.simulation5;

import com.example.simulation5.gui.SectiiController;
import com.example.simulation5.repository.ConsultatiiDbRepository;
import com.example.simulation5.repository.MediciDbRepository;
import com.example.simulation5.repository.SectieDbRepository;
import com.example.simulation5.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static String[] sysArgs;
    private Service service;

    private void setup() {
        String urlDb = "jdbc:postgresql://localhost:5432/Simulare5";
        String usernameDb = "postgres";
        String passDb = "1234";
        SectieDbRepository sectieDbRepository = new SectieDbRepository(urlDb, usernameDb, passDb);
        MediciDbRepository mediciDbRepository = new MediciDbRepository(urlDb, usernameDb, passDb);
        ConsultatiiDbRepository consultatiiDbRepository = new ConsultatiiDbRepository(urlDb, usernameDb, passDb);
        service = new Service(sectieDbRepository, mediciDbRepository, consultatiiDbRepository);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setup();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("sectii-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SectiiController sectiiController = fxmlLoader.getController();
        sectiiController.setService(service);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        sysArgs = args;
        launch();
    }
}