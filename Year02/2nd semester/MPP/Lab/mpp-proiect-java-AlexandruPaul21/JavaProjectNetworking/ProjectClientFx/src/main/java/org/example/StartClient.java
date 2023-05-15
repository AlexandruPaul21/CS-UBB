package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.gui.LoginController;
import org.example.gui.MainViewController;
import org.example.protocol.ProjectServicesProxy;

import java.io.IOException;
import java.util.Properties;

public class StartClient extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Properties clientProperties = new Properties();

        try {
            clientProperties.load(StartClient.class.getResourceAsStream("/project_client.properties"));
        } catch (IOException e) {
            return;
        }

        String serverIP = clientProperties.getProperty("project.server.host");
        int serverPort = 55555;

        try {
            serverPort = Integer.parseInt(clientProperties.getProperty("project.server.port"));
        } catch (NumberFormatException e) {
        }

        System.out.println("Using server on host: " + serverIP);
        System.out.println("Using server on port: " + serverPort);

        IProjectServices server = new ProjectServicesProxy(serverIP, serverPort);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root = fxmlLoader.load();

        LoginController ctrl = fxmlLoader.getController();
        ctrl.setServer(server);

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/main-view.fxml"));
        Parent mainRoot = mainLoader.load();

        MainViewController projectCtrl = mainLoader.getController();
        projectCtrl.setServer(server);

        ctrl.setProjectCtrl(projectCtrl);
        ctrl.setMainParent(mainRoot);

        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}