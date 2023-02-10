package com.example.simulation2.gui;

import com.example.simulation2.HelloApplication;
import com.example.simulation2.domain.Client;
import com.example.simulation2.service.ClientService;
import com.example.simulation2.service.FlightService;
import com.example.simulation2.service.TicketService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {


    public Label errorLabel;
    public TextField usernameTextField;
    private ClientService clientService;
    private FlightService flightService;
    private TicketService ticketService;

    public void loginHandle() throws IOException {
        if (usernameTextField.getText().isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("You did not introduce an username");
        } else {
            String username = usernameTextField.getText();
            Client client = clientService.findOne(username);
            if (client == null) {
                errorLabel.setVisible(true);
                errorLabel.setText("You are not registered as a client");
            } else {
                client.setId(username);
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-window.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                FlightTableController ctr = fxmlLoader.getController();
                ctr.setAll(flightService, ticketService, client);

                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle(client.getName());
                stage.show();

                closeWindow();
            }
        }
    }

    public void setService(ClientService clientService, FlightService flightService, TicketService ticketService) {
        this.clientService = clientService;
        this.flightService = flightService;
        this.ticketService = ticketService;
    }

    private void closeWindow() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    public void newSessionRequest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Login login = fxmlLoader.getController();
        login.setService(clientService, flightService, ticketService);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
