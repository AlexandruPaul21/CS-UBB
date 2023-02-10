package com.example.simularemap;

import com.example.simularemap.domain.Client;
import com.example.simularemap.repository.dbRepo.*;
import com.example.simularemap.service.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private HotelService hotelService;
    private LocationService locationService;
    private SpecialOfferService specialOfferService;
    private ClientsService clientsService;
    private ReservationService reservationService;
    private static String[] sysArgs;

    private void start() {
        String urlDb = "jdbc:postgresql://localhost:5432/Simulare";
        String usernameDb = "postgres";
        String passDb = "1234";
        HotelsDbRepository hotelsDbRepository = new HotelsDbRepository(urlDb, usernameDb, passDb);
        LocationsDbRepo locationsDbRepo = new LocationsDbRepo(urlDb, usernameDb, passDb);
        SpecialOfferDbRepo specialOfferDbRepo = new SpecialOfferDbRepo(urlDb, usernameDb, passDb);
        ClientsDbRepo clientsDbRepo = new ClientsDbRepo(urlDb, usernameDb, passDb);
        ReservationDbRepo reservationDbRepo = new ReservationDbRepo(urlDb, usernameDb, passDb);
        hotelService = new HotelService(hotelsDbRepository);
        locationService = new LocationService(locationsDbRepo);
        specialOfferService = new SpecialOfferService(specialOfferDbRepo);
        clientsService = new ClientsService(clientsDbRepo);
        reservationService = new ReservationService(reservationDbRepo);
    }

    @Override
    public void start(Stage stage) throws IOException {
        start();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainController ctr = fxmlLoader.getController();
        ctr.setServices(hotelService, locationService, specialOfferService, clientsService, reservationService);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        for (String id : sysArgs) {
            Client client = clientsService.findById(Long.parseLong(id));
            FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("clients-offers.fxml"));
            Scene scene1 = new Scene(fxmlLoader1.load());
            ClientsOffer ctr1 = fxmlLoader1.getController();
            ctr1.setServiceClient(clientsService, client);
            reservationService.addObserver(ctr1);
            Stage stage1 = new Stage();
            stage1.setTitle(client.getName());
            stage1.setScene(scene1);
            stage1.show();
        }
    }

    public static void main(String[] args) {
        sysArgs = args;
        launch();
    }
}