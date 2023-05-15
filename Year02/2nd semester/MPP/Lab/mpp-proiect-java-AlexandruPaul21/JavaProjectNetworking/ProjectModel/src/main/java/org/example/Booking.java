package org.example;

import java.util.List;

public class Booking extends Entity<Long> {

    private Flight flight;
    private Client client;
    private List<String> passengers;

    public Booking(Flight flight, Client client, List<String> passengers) {
        this.flight = flight;
        this.client = client;
        this.passengers = passengers;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<String> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<String> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "flight=" + flight +
                ", client=" + client +
                ", passengers=" + passengers +
                '}';
    }
}
