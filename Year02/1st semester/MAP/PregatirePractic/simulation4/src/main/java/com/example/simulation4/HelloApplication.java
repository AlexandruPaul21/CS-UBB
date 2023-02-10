package com.example.simulation4;

import com.example.simulation4.gui.MainController;
import com.example.simulation4.gui.MainControllerChild;
import com.example.simulation4.repository.PacientDbRepository;
import com.example.simulation4.repository.PatDbRepository;
import com.example.simulation4.service.PacientService;
import com.example.simulation4.service.PatService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static String[] sysArgs;
    private PatService patService;
    private PacientService pacientService;

    private void setup() {
        String urlDb = "jdbc:postgresql://localhost:5432/Simulare4";
        String usernameDb = "postgres";
        String passDb = "1234";
        PatDbRepository patDbRepository = new PatDbRepository(urlDb, usernameDb, passDb);
        PacientDbRepository pacientDbRepository = new PacientDbRepository(urlDb, usernameDb, passDb);
        patService = new PatService(patDbRepository);
        pacientService = new PacientService(pacientDbRepository);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setup();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController mainController = fxmlLoader.getController();
        mainController.setService(patService, pacientService);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("main-window2.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());
        MainControllerChild mainControllerChild = fxmlLoader1.getController();
        mainControllerChild.setService(patService, pacientService);
        Stage stage1 = new Stage();
        stage1.setTitle("Pacienti");
        stage1.setScene(scene1);
        stage1.show();
    }

    public static void main(String[] args) {
        sysArgs = args;
        launch();
    }
}