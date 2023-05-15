package org.example.persistance;

import org.example.model.Booking;

public interface IBookingRepository extends CrudRepository<Long, Booking> {
    int getNumberOfBookingsForFlight(Long id);
    Long getLowestAvbId();
}
