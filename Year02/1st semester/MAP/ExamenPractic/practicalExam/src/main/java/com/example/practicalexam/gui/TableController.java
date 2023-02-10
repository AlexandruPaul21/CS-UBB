package com.example.practicalexam.gui;

import com.example.practicalexam.constants.PriceConstants;
import com.example.practicalexam.domain.City;
import com.example.practicalexam.domain.DTOs.View;
import com.example.practicalexam.domain.TrainStation;
import com.example.practicalexam.service.Service;
import com.example.practicalexam.utils.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.List;
import java.util.stream.Collectors;

public class TableController implements Observer {
    public Label viewTogether;
    public Label errorLabel;
    ObservableList<City> cities = FXCollections.observableArrayList();
    ObservableList<String> routes = FXCollections.observableArrayList();

    public ComboBox<City> departureCombo;
    public ComboBox<City> destinationCombo;
    public CheckBox directRoute;
    public ListView<String> listViewTrains;

    private Service service;

    private City displayedFrom;
    private City displayedTo;

    private String format(List<TrainStation> trainStations) {
        String ans;
        if (trainStations.isEmpty()) {
            return "";
        }
        ans = service.getCityById(trainStations.get(0).getDepartureCityId()).getName();
        for (TrainStation ts : trainStations) {
            String dest = service.getCityById(ts.getDestinationCityId()).getName();
            ans= ans + " - " + ts.getTrainId() + " -> " + dest;
        }
        ans += ", price: " + PriceConstants.PRICE_PER_STATION * trainStations.size();
        return ans;
    }

    public void searchDetected() {
        if (departureCombo.getValue() == null || destinationCombo.getValue() == null) {
            errorLabel.setText("You have to select both the departure and the destination points");
            return;
        }

        if (destinationCombo.getValue().equals(departureCombo.getValue())) {
            errorLabel.setText("Destination and departure cannot be the same");
            return;
        }

        if (directRoute.isSelected()) {
            City depCity = departureCombo.getValue();
            City destCity = destinationCombo.getValue();
            if (displayedFrom != null) {
                service.change(displayedFrom, displayedTo);
            }
            displayedFrom = depCity;
            displayedTo = destCity;
            List<List<TrainStation>> trainStations = service.getAllDirectRoutes(depCity.getId(), destCity.getId());
            if (trainStations == null || trainStations.isEmpty()) {
                routes.clear();
                errorLabel.setText("There are not routes for the selection");
                return;
            }
            errorLabel.setText("");
            routes.setAll(trainStations
                    .stream()
                    .map(this::format)
                    .collect(Collectors.toList()));
        } else {
            City depCity = departureCombo.getValue();
            City destCity = destinationCombo.getValue();
            if (displayedFrom != null) {
                service.change(displayedFrom, displayedTo);
            }
            displayedFrom = depCity;
            displayedTo = destCity;
            List<List<TrainStation>> trainStations = service.getNotDirectedRoutes(depCity.getId(), destCity.getId());
            if (trainStations == null || trainStations.isEmpty()) {
                routes.clear();
                errorLabel.setText("There are not routes for the selection");
                return;
            }
            errorLabel.setText("");
            routes.setAll(trainStations
                    .stream()
                    .map(this::format)
                    .collect(Collectors.toList()));
        }
    }

    @FXML
    private void initialize() {
        departureCombo.setItems(cities);;
        destinationCombo.setItems(cities);
        listViewTrains.setItems(routes);
        viewTogether.setText("");
    }

    public void setService(Service service) {
        this.service = service;
        cities.setAll(service.getAllCities());
        this.service.addObserver(this);
    }

    @Override
    public void update(City city1, City city2) {
        if (displayedTo == null || displayedFrom == null) {
            return;
        }
        if (!city1.getId().equals(displayedFrom.getId()) || !city2.getId().equals(displayedTo.getId())) {
            return;
        }
        List<View> views = service.getViews();
        for (View view : views) {
            if (view.getCity1().getId().equals(city1.getId()) && view.getCity2().getId().equals(city2.getId())) {
                if (view.app == 0) {
                    viewTogether.setText("");
                } else {
                    viewTogether.setText(view.app + " other users(s) are looking at this route");
                }
            }
        }
    }
}
