package org.example.persistance;

import org.example.model.Flight;
import java.time.LocalDateTime;

public interface IFlightRepository extends CrudRepository<Long, Flight> {
    Iterable<Flight> findFlightByDestinationAndDate(String destination, LocalDateTime dateTime);
}
