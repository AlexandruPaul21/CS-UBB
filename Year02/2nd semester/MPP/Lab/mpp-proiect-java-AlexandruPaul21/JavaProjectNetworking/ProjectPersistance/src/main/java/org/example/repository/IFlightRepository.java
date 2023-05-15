package org.example.repository;

import org.example.Flight;

import java.time.LocalDateTime;

public interface IFlightRepository extends CrudRepository<Long, Flight> {
    Iterable<Flight> findFlightByDestinationAndDate(String destination, LocalDateTime dateTime);
}
