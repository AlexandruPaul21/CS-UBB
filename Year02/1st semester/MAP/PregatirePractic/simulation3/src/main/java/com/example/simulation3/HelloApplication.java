package com.example.simulation3;

import com.example.simulation3.gui.LoginController;
import com.example.simulation3.repository.NeedDbRepository;
import com.example.simulation3.repository.PersonDbRepository;
import com.example.simulation3.service.NeedService;
import com.example.simulation3.service.PersonService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static String[] sysArgs;
    private PersonService personService;
    private NeedService needService;

    private void setup() {
        String urlDb = "jdbc:postgresql://localhost:5432/Simulare3";
        String usernameDb = "postgres";
        String passDb = "1234";
        PersonDbRepository personDbRepository = new PersonDbRepository(urlDb, usernameDb, passDb);
        NeedDbRepository needDbRepository = new NeedDbRepository(urlDb, usernameDb, passDb);
        personService = new PersonService(personDbRepository);
        needService = new NeedService(needDbRepository);
    }

    @Override
    public void start(Stage stage) throws IOException {
        setup();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-window.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        LoginController loginController = fxmlLoader.getController();
        loginController.setService(personService, needService);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        sysArgs = args;
        launch();
    }
}