package com.example.javafxproject.gui;

import com.example.javafxproject.Main;
import com.example.javafxproject.model.Client;
import com.example.javafxproject.model.Flight;
import com.example.javafxproject.model.dto.FlightDto;
import com.example.javafxproject.service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MainViewController {
    ObservableList<FlightDto> model = FXCollections.observableArrayList();

    public TextField nameTextEdit;
    public TextField addressTextEdit;
    public TextField passengersTextEdit;

    public TextField destinationTextEdit;
    public Button searchButton;
    public TableColumn<FlightDto, String> destinationColumn;
    public TableColumn<FlightDto, String> departureColumn;
    public TableColumn<FlightDto, String> airportColumn;
    public TableColumn<FlightDto, String> avbSeatsColumn;
    public Button bookButton;
    public DatePicker departureDatePicker;
    public TableView<FlightDto> flightsTable;


    private Service service;

    public void setService(Service service) {
        this.service = service;
        initializeFields();
        populateTable();
    }

    private void populateTable() {
        List<Flight> flights = (List<Flight>) service.getAllFlights();
        model.setAll(flights.stream().map(flight -> new FlightDto(flight, service)).toList());
    }

    private void initializeFields() {
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        airportColumn.setCellValueFactory(new PropertyValueFactory<>("airport"));
        avbSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("avbSeats"));
        flightsTable.setItems(model);
    }

    public void searchClicked() {
        LocalDateTime localDateTime = departureDatePicker.getValue().atStartOfDay();
        String destination = destinationTextEdit.getText();

        List<Flight> flights = (List<Flight>) service.getFlightsForDestinationAndDate(destination, localDateTime);
        model.setAll(flights.stream().map(flight -> new FlightDto(flight, service)).toList());
    }

    public void bookClicked() throws IOException {
        FlightDto selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No flight selected");
            alert.setContentText("Please select a flight to book");
            alert.showAndWait();
            return;
        }

        String name = nameTextEdit.getText();
        String address = addressTextEdit.getText();
        String passengersString = passengersTextEdit.getText();
        List<String> passengers = List.of(passengersString.split(","));

        if (name.isEmpty() || address.isEmpty() || passengersString.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Empty fields");
            alert.setContentText("Please fill in all fields");
            alert.showAndWait();
            return;
        }

        boolean succeeded = service.bookFlight(selectedFlight.getId(), name, address, passengers);
        if (!succeeded) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Booking successful");
            alert.setContentText("Booking successful");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Booking failed");
        alert.setContentText("Booking failed");
        alert.showAndWait();
    }

    public void clearButtonClicked() {
        destinationTextEdit.setText("");
        departureDatePicker.setValue(null);
        populateTable();
    }
}
