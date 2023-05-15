package org.example.gui;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.*;
import org.example.dto.DtoUtils;
import org.example.dto.FlightDto;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, IProjectObserver {
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
    public Label flightDetailsLabel;

    private IProjectServices server;
    private Agency loggedAgency;

    private List<FlightDto> selectedFlights;
    private FlightDto selectedFlightDto;
    private int avbSeats;

    public void setLoggedAgency(Agency loggedAgency) {
        this.loggedAgency = loggedAgency;
    }

    public MainViewController() {

    }

    public MainViewController(IProjectServices server) {
        this.server = server;
    }

    public void setServer(IProjectServices server) {
        this.server = server;
        initializeFields();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void ticketBuyed() {
        Platform.runLater(() -> {
            model.setAll(selectedFlights);
            flightsTable.getSelectionModel().select(selectedFlightDto);
            try {
                tableSelectionChanged();
            } catch (ProjectException e) {
                e.printStackTrace();
            }
        });
    }

    private void populateTable() throws ProjectException {
        Flight[] flights = server.getAllFlights(loggedAgency, this);
        List<Flight> flightList = Arrays.stream(flights).toList();
        model.setAll(flightList.stream().map(FlightDto::new).toList());
        selectedFlights = flightList.stream().map(FlightDto::new).toList();
    }

    private void initializeFields() {
        destinationColumn.setCellValueFactory(new PropertyValueFactory<>("destination"));
        departureColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        airportColumn.setCellValueFactory(new PropertyValueFactory<>("airport"));
        avbSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("avbSeats"));
        flightsTable.setItems(model);
    }

    public void searchClicked() throws ProjectException {
        String destination = destinationTextEdit.getText();
        LocalDateTime departure = departureDatePicker.getValue().atStartOfDay();
        Flight flight = new Flight(destination, departure);

        Flight[] flights = server.getFlightsForDestinationAndDate(flight, this);
        List<Flight> flightList = Arrays.stream(flights).toList();
        model.setAll(flightList.stream().map(FlightDto::new).toList());
        selectedFlights = flightList.stream().map(FlightDto::new).toList();
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

        if (passengers.size() + 1 > Integer.parseInt(selectedFlight.getAvbSeats()) - avbSeats) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Not enough seats");
            alert.setContentText("There are not enough seats for the selected flight");
            alert.showAndWait();
            return;
        }

        Booking booking = new Booking(DtoUtils.fromDto(selectedFlight), new Client(name, address), passengers);

        try {
            server.bookFlight(booking, this);
        } catch (ProjectException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Booking failed");
            alert.setContentText("Booking failed");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Booking successful");
        alert.setContentText("Booking successful");
        alert.showAndWait();
        return;
    }

    public void clearButtonClicked() throws ProjectException {
        destinationTextEdit.setText("");
        departureDatePicker.setValue(null);
        populateTable();
    }

    public void logout() {
        try {
            server.logout(loggedAgency, this);
            closeWindow();
        } catch (ProjectException e) {
            System.out.println("Logout error " + e);
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) destinationTextEdit.getScene().getWindow();
        stage.close();
    }

    public void tableSelectionChanged() throws ProjectException {
        FlightDto selectedFlight = flightsTable.getSelectionModel().getSelectedItem();
        if (selectedFlight == null) {
            flightDetailsLabel.setText("");
            return;
        }

        selectedFlightDto = selectedFlight;
        avbSeats = server.getNumberOfSeats(DtoUtils.fromDto(selectedFlight), this);

        flightDetailsLabel.setText("The selected flight with destination " + selectedFlight.getDestination() + " has " +
                avbSeats + " booked seats");
    }
}
