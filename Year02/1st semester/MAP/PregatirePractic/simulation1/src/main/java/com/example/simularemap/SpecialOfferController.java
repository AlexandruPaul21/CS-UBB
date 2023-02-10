package com.example.simularemap;

import com.example.simularemap.domain.Dtos.HotelDTO;
import com.example.simularemap.domain.Dtos.SpecialOfferDTO;
import com.example.simularemap.domain.Reservation;
import com.example.simularemap.service.HotelService;
import com.example.simularemap.service.ReservationService;
import com.example.simularemap.service.SpecialOfferService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SpecialOfferController {
    ObservableList<SpecialOfferDTO> offers = FXCollections.observableArrayList();

    private HotelService hotelService;
    private SpecialOfferService specialOfferService;
    private HotelDTO hotel;
    private ReservationService reservationService;

    public void setServices(HotelService hotelService, SpecialOfferService specialOfferService, ReservationService reservationService) {
        this.hotelService = hotelService;
        this.specialOfferService = specialOfferService;
        this.reservationService = reservationService;
    }

    public void setHotel(HotelDTO hotel) {
        this.hotel = hotel;
    }

    @FXML
    private DatePicker datePickerBegin;

    @FXML
    private DatePicker datePickerEnd;

    @FXML
    private TableView<SpecialOfferDTO> offersTableView;

    @FXML
    private TableColumn<SpecialOfferDTO, String> startDateCol;

    @FXML
    private TableColumn<SpecialOfferDTO, String> endDateCol;

    @FXML
    private TableColumn<SpecialOfferDTO, String> percentsCol;

    @FXML
    private void initialize() {
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        percentsCol.setCellValueFactory(new PropertyValueFactory<>("percent"));

        offersTableView.setItems(offers);
    }

    public void searchBtnHandle() {
        Date startDate = Date.from(datePickerBegin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(datePickerEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        offers.setAll(specialOfferService.findByDateAndId(hotel.getId(), startDate, endDate)
                .stream()
                .map(SpecialOfferDTO::new)
                .collect(Collectors.toList()));
    }

    public void reserveButton() {
        Date startDate = Date.from(datePickerBegin.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(datePickerEnd.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        LocalDateTime startDateTime = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        long diffInMills = Math.abs(endDate.getTime() - startDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS);
        int noDays = (int) diff;
        Reservation reservation = new Reservation(1L, hotel.getId(), startDateTime, noDays);
        reservation.setId(reservationService.getLowestId());
        reservationService.save(reservation);
    }
}
