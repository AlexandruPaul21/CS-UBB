package com.example.simularemap;

import com.example.simularemap.domain.Client;
import com.example.simularemap.domain.Dtos.OfferDTO;
import com.example.simularemap.service.ClientsService;
import com.example.simularemap.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClientsOffer implements Observer {
    public Label testLabelObs;
    ObservableList<OfferDTO> offers = FXCollections.observableArrayList();

    private ClientsService clientsService;
    private Client client;

    public void update() {
        testLabelObs.setText("Another client which likes x booked");
    }

    @FXML
    private TableView<OfferDTO> tableViewOffers;

    @FXML
    private TableColumn<OfferDTO, String> hoNameCol;

    @FXML
    private TableColumn<OfferDTO, String> locNameCol;

    @FXML
    private TableColumn<OfferDTO, String> startCol;

    @FXML
    private TableColumn<OfferDTO, String> endCol;

    @FXML
    private void initialize() {
        hoNameCol.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        locNameCol.setCellValueFactory(new PropertyValueFactory<>("locationName"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        testLabelObs.setText("");
        tableViewOffers.setItems(offers);
    }

    public void setServiceClient(ClientsService clientsService, Client client) {
        this.clientsService = clientsService;
        this.client = client;
        offers.setAll(clientsService.findByPercentage(client.getFidelityGrade()));
    }


}
