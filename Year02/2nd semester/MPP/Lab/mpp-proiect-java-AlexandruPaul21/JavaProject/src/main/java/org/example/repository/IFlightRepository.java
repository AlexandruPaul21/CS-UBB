package org.example.repository;

import org.example.model.Flight;

import java.time.LocalDateTime;
import java.util.List;

public interface IFlightRepository extends CrudRepository<Long, Flight> {
    Iterable<Flight> findFlightByDestinationAndDate(String destination, LocalDateTime dateTime);
}
