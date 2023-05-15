package org.example.dto;

import org.example.Agency;
import org.example.Booking;
import org.example.Flight;

import java.time.LocalDateTime;

public class DtoUtils {
    public static AgencyDto getDto(Agency agency) {
        String username = agency.getId();
        String name = agency.getName();
        String password = agency.getPassword();
        return new AgencyDto(username, name, password);
    }

    public static Agency fromDto(AgencyDto agency) {
        String username = agency.getUsername();
        String name = agency.getName();
        String password = agency.getPassword();
        return new Agency(username, name, password);
    }

    public static Flight fromDto(FlightDto flightDto) {
        Flight flight = new Flight(flightDto.getDestination(), LocalDateTime.parse(flightDto.getDeparture()), flightDto.getAirport(), Integer.parseInt(flightDto.getAvbSeats()));
        flight.setId(flightDto.getId());
        return flight;
    }

    public static FlightDto getDto(Flight flight) {
        return new FlightDto(flight);
    }

    public static BookingDto getDto(Booking booking) {
        return new BookingDto(booking.getId(), booking.getFlight(), booking.getClient(), booking.getPassengers());
    }

    public static Booking fromDto(BookingDto bookingDto) {
        Booking booking = new Booking(bookingDto.getFlight(), bookingDto.getClient(), bookingDto.getPassengers());
        booking.setId(bookingDto.getId());
        return booking;
    }

    public static Flight[] fromDto(FlightDto[] flightDtos) {
        Flight[] flights = new Flight[flightDtos.length];
        for (int i = 0; i < flightDtos.length; ++i) {
            flights[i] = fromDto(flightDtos[i]);
        }

        return flights;
    }

    public static FlightDto[] getDto(Flight[] flights) {
        FlightDto[] flightDtos = new FlightDto[flights.length];
        for (int i = 0; i < flights.length; ++i) {
            flightDtos[i] = getDto(flights[i]);
        }

        return flightDtos;
    }

}
