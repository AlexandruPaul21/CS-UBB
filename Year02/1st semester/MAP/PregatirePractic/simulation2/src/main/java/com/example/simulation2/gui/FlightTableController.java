package com.example.simulation2.gui;

import com.example.simulation2.domain.Client;
import com.example.simulation2.domain.DTOs.FlightDTO;
import com.example.simulation2.domain.Ticket;
import com.example.simulation2.service.FlightService;
import com.example.simulation2.service.TicketService;
import com.example.simulation2.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class FlightTableController implements Observer {
    private final int rowsPerPage = 2;
    private int actualPage = 1;
    private int totalPage;
    private List<FlightDTO> actualData;

    public Label errorLabel;
    public TableColumn<FlightDTO, String> avbColumn;
    public Label pagerLabel;
    ObservableList<FlightDTO> flights = FXCollections.observableArrayList();
    ObservableList<String> from = FXCollections.observableArrayList();
    ObservableList<String> to = FXCollections.observableArrayList();

    public ComboBox<String> fromComboBox;
    public ComboBox<String> toComboBox;
    public DatePicker datePicker;
    public TableView<FlightDTO> flightsTableView;
    public TableColumn<FlightDTO, String> departureTimeColumn;
    public TableColumn<FlightDTO, String>  landingTimeColumn;
    public TableColumn<FlightDTO, String>  seatsColumn;

    private FlightService flightService;
    private Client client;
    private TicketService ticketService;

    private String fromS;
    private String toS;
    private Date dateS;

    public void setAll(FlightService flightService, TicketService ticketService, Client client) {
        this.flightService = flightService;
        this.client = client;
        this.ticketService = ticketService;
        this.flightService.addObserver(this);
        from.setAll(flightService.getAllFrom());
        to.setAll(flightService.getAllTo());
    }



    @FXML
    private void initialize() {
        departureTimeColumn.setCellValueFactory(new PropertyValueFactory<>("departure"));
        landingTimeColumn.setCellValueFactory(new PropertyValueFactory<>("arrival"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<>("seats"));
        avbColumn.setCellValueFactory(new PropertyValueFactory<>("available"));

        flightsTableView.setItems(flights);
        toComboBox.setItems(to);
        fromComboBox.setItems(from);
    }


    public void searchClicked() {
        if (fromComboBox.getValue() == null || toComboBox.getValue() == null) {
            errorLabel.setText("Error");
            return;
        }

        String from = fromComboBox.getValue();
        String to = toComboBox.getValue();

        if (datePicker.getValue() == null) {
            errorLabel.setText("Error!");
            return;
        }

        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        fromS = from;
        toS = to;
        dateS = date;
        List<FlightDTO> data = flightService.getFLightsForRoute(from, to, date)
                .stream()
                .map(FlightDTO::new).toList();
        actualData = data;
        totalPage = data.size() / rowsPerPage + (data.size() % rowsPerPage);
        pagerLabel.setText("1/" + totalPage);
        flights.setAll(data.subList(0,2));
    }

    public void handleBuy() {
        if (flightsTableView.getSelectionModel().isEmpty()) {
            return;
        }

        FlightDTO flightDTO = flightsTableView.getSelectionModel().getSelectedItem();
        Ticket ticket = new Ticket(client.getId(), flightDTO.getId(), null);
        Ticket ans = ticketService.save(ticket);
        if (ans == null) {
            errorLabel.setText("Failed");
        } else {
            flightService.book(flightDTO.getId());
            setFlights(actualPage);
            errorLabel.setText("Success");
        }
    }

    @Override
    public void update() {
        actualData = flightService.getFLightsForRoute(fromS, toS, dateS)
                .stream()
                .map(FlightDTO::new)
                .collect(Collectors.toList());
        setFlights(actualPage);
    }

    public void prevPage() {
        if (actualPage == 1) {
            return;
        }
        actualPage = actualPage - 1;
        setFlights(actualPage);
    }

    public void nextPage() {
        if (actualPage == totalPage) {
            return;
        }
        actualPage = actualPage + 1;
        setFlights(actualPage);
    }

    private void setFlights(int actualPage) {
        pagerLabel.setText(actualPage + "/" + totalPage);
        flights.setAll(actualData.subList((actualPage - 1) * rowsPerPage, Math.min(actualPage * rowsPerPage, actualData.size())));
    }
}
