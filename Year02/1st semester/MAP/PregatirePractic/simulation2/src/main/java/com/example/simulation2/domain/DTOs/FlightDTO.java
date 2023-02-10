package com.example.simulation2.domain.DTOs;

import com.example.simulation2.domain.Flight;

public class FlightDTO {
    private Long id;
    private String departure;
    private String arrival;
    private String seats;
    private String available;

    public FlightDTO(Flight flight) {
        this.id = flight.getId();
        this.departure = flight.getDepartureTime().toString();
        this.arrival = flight.getLandingTime().toString();
        this.seats = String.valueOf(flight.getSeats());
        this.available = String.valueOf(flight.getSeats() - flight.getBooked());
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats;
    }
}
