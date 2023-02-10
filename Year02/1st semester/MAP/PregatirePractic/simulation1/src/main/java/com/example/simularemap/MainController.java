package com.example.simularemap;

import com.example.simularemap.domain.Dtos.HotelDTO;
import com.example.simularemap.domain.Location;
import com.example.simularemap.service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;

public class MainController {
    private HotelService hotelService;
    private LocationService locationService;
    private Location selectedLocation;

    ObservableList<HotelDTO> hotels = FXCollections.observableArrayList();
    ObservableList<Location> locations = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Location> locationsComboBox;

    @FXML
    private TableView<HotelDTO> hotelsTableView;

    @FXML
    private TableColumn<HotelDTO, String> hotelNameColumn;

    @FXML
    private TableColumn<HotelDTO, String> roomsColumn;

    @FXML
    private TableColumn<HotelDTO, String> priceColumn;

    @FXML
    private TableColumn<HotelDTO, String> typeColumn;
    private SpecialOfferService specialOfferService;
    private ClientsService clientService;
    private ReservationService reservationService;


    @FXML
    private void initialize() {
        hotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("hotelName"));
        roomsColumn.setCellValueFactory(new PropertyValueFactory<>("noRooms"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("pricePerNight"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("hotelType"));

        hotelsTableView.setItems(hotels);
        locationsComboBox.setItems(locations);
        locationsComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                Location selected = locationsComboBox.getValue();
                refreshTable(selected);
            }
        });
    }

    public void setServices(HotelService hotelService, LocationService locationService, SpecialOfferService specialOfferService, ClientsService clientsService, ReservationService reservationService) {
        this.hotelService = hotelService;
        this.locationService = locationService;
        this.specialOfferService = specialOfferService;
        this.clientService = clientsService;
        this.reservationService = reservationService;
        locations.setAll(locationService.findAll());
    }

    private void refreshTable(Location selected) {
        hotels.setAll(hotelService.findByLocationId(selected.getId())
                .stream()
                .map(HotelDTO::new)
                .collect(Collectors.toList()));
    }

    public void tableClick() throws IOException {
        if (hotelsTableView.getSelectionModel().isEmpty()) {
            return;
        }
        HotelDTO hotelDTO = hotelsTableView.getSelectionModel().getSelectedItem();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("special-offer.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        SpecialOfferController specialOfferController = fxmlLoader.getController();
        specialOfferController.setServices(hotelService, specialOfferService, reservationService);
        specialOfferController.setHotel(hotelDTO);

        Stage stage = new Stage();
        stage.setTitle("Offers for " + hotelDTO.getHotelName());
        stage.setScene(scene);
        stage.show();
    }
}