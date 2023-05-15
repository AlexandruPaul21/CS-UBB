package org.example.model;

import java.time.LocalDateTime;

public class Flight extends Entity<Long> {

    private String destination;
    private LocalDateTime departureDateTime;
    private String airport;
    private int availableSeats;

    public Flight(String destination, LocalDateTime departureDateTime, String airport, int availableSeats) {
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.airport = airport;
        this.availableSeats = availableSeats;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "destination='" + destination + '\'' +
                ", departureDateTime=" + departureDateTime +
                ", airport='" + airport + '\'' +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
