package com.example.javafxproject.repository;


import com.example.javafxproject.model.Flight;

import java.time.LocalDateTime;

public interface IFlightRepository extends CrudRepository<Long, Flight> {
    Iterable<Flight> findFlightByDestinationAndDate(String destination, LocalDateTime dateTime);
}
