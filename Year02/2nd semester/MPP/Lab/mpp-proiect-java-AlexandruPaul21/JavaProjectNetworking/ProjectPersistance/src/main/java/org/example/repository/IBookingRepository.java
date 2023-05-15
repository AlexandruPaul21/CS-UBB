package org.example.repository;

import org.example.Booking;

public interface IBookingRepository extends CrudRepository<Long, Booking> {
    int getNumberOfBookingsForFlight(Long id);
    Long getLowestAvbId();
}
