package org.example.dto;

import org.example.Client;
import org.example.Flight;

import java.io.Serializable;
import java.util.List;

public class BookingDto implements Serializable {
    private Long id;
    private Flight flight;
    private Client client;
    private List<String> passengers;

    public BookingDto(Long id, Flight flight, Client client, List<String> passangers) {
        this.id = id;
        this.flight = flight;
        this.client = client;
        this.passengers = passangers;
    }

    public Long getId() {
        return id;
    }

    public Flight getFlight() {
        return flight;
    }

    public Client getClient() {
        return client;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }
}
