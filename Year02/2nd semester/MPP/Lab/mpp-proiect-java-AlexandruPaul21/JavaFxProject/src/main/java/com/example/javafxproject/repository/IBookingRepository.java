package com.example.javafxproject.repository;


import com.example.javafxproject.model.Booking;
import com.example.javafxproject.model.Client;

import java.util.List;

public interface IBookingRepository extends CrudRepository<Long, Booking> {
    int getNumberOfBookingsForFlight(Long id);
    Long getLowestAvbId();
}
