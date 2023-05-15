package com.example.javafxproject.model.dto;

import com.example.javafxproject.model.Flight;
import com.example.javafxproject.service.Service;

public class FlightDto {
    private Long id;
    private String destination;
    private String departure;
    private String airport;
    private String avbSeats;

    public FlightDto(Flight flight, Service service) {
        this.id = flight.getId();
        this.destination = flight.getDestination();
        this.departure = flight.getDepartureDateTime().toString();
        this.airport = flight.getAirport();
        this.avbSeats = String.valueOf(flight.getAvailableSeats() - service.getNumberOfBookingsForFlight(flight.getId()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String airport) {
        this.airport = airport;
    }

    public String getAvbSeats() {
        return avbSeats;
    }

    public void setAvbSeats(String avbSeats) {
        this.avbSeats = avbSeats;
    }
}
