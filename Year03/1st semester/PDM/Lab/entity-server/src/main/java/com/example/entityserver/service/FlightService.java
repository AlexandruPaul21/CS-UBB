package com.example.entityserver.service;

import com.example.entityserver.model.dto.FlightDto;

import java.util.List;

public interface FlightService {
    List<FlightDto> getAllFlights();
    FlightDto getFlightById(Long id);
    void deleteFlight(Long id);
    FlightDto save(FlightDto flightDto);
}
