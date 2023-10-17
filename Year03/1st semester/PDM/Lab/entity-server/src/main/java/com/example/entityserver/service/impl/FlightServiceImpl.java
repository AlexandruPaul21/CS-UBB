package com.example.entityserver.service.impl;

import com.example.entityserver.model.Flight;
import com.example.entityserver.model.dto.FlightDto;
import com.example.entityserver.model.mapper.FlightMapper;
import com.example.entityserver.repository.FlightRepository;
import com.example.entityserver.service.FlightService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private FlightRepository flightRepository;
    private FlightMapper flightMapper;

    @Autowired
    public void setFlightMapper(FlightMapper flightMapper) {
        this.flightMapper = flightMapper;
    }

    @Autowired
    public void setFlightRepository(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    @Override
    @Transactional
    public List<FlightDto> getAllFlights() {
        return flightRepository.findAll().stream().map(flight -> flightMapper.entityToDto(flight)).toList();
    }

    @Override
    @Transactional
    public FlightDto getFlightById(Long id) {
        return flightMapper.entityToDto(flightRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    @Override
    @Transactional
    public FlightDto save(FlightDto flight) {
        Flight savedFlight = flightRepository.save(flightMapper.dtoToEntity(flight));
        return flightMapper.entityToDto(savedFlight);
    }
}
