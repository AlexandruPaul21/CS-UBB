package com.example.javafxproject.service;

import com.example.javafxproject.model.Booking;
import com.example.javafxproject.model.Client;
import com.example.javafxproject.model.Flight;
import com.example.javafxproject.repository.*;

import java.time.LocalDateTime;
import java.util.List;

public class Service {
    private final IAgencyRepository agencyRepository;
    private final IBookingRepository bookingRepository;
    private final IClientRepository clientRepository;
    private final IFlightRepository flightRepository;

    public Service(IAgencyRepository agencyRepository, IBookingRepository bookingRepository, IClientRepository clientRepository, IFlightRepository flightRepository) {
        this.agencyRepository = agencyRepository;
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.flightRepository = flightRepository;
    }

    public boolean validateLogin(String username, String password) {
        return agencyRepository.existsUser(username, password);
    }

    public Iterable<Flight> getFlightsForDestinationAndDate(String destination, LocalDateTime date) {
        return flightRepository.findFlightByDestinationAndDate(destination, date);
    }

    public int getNumberOfBookingsForFlight(Long id) {
        return bookingRepository.getNumberOfBookingsForFlight(id);
    }

    public Iterable<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public boolean bookFlight(Long flightId, String clientName, String clientAddress, List<String> clients) {
        Client client = new Client(clientName, clientAddress);
        client.setId(clientRepository.getLowestAvbId() + 1);
        clientRepository.save(client);
        Booking booking = new Booking(flightRepository.findOne(flightId), client, clients);
        booking.setId(bookingRepository.getLowestAvbId() + 1);
        return bookingRepository.save(booking) != null;
    }
}
