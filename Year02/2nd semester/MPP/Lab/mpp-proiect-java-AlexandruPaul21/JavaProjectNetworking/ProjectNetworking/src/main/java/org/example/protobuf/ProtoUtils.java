package org.example.protobuf;

import com.google.protobuf.Timestamp;
import org.example.Agency;
import org.example.Booking;
import org.example.Flight;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class ProtoUtils {
    public static ProjectProto.ProjectRequest createLoginRequest(Agency agency) {
        ProjectProto.Agency agencyDto = ProjectProto.Agency.newBuilder()
                .setUsername(agency.getId()).setName(agency.getName()).setPassword(agency.getPassword()).build();

        return ProjectProto.ProjectRequest.newBuilder()
                .setType(ProjectProto.ProjectRequest.Type.Login)
                .setAgency(agencyDto)
                .build();
    }

    public static ProjectProto.ProjectRequest createLogoutRequest(Agency agency) {
        ProjectProto.Agency agencyDto = ProjectProto.Agency.newBuilder()
                .setUsername(agency.getId()).setName(agency.getName()).setPassword(agency.getPassword()).build();

        return ProjectProto.ProjectRequest.newBuilder()
                .setType(ProjectProto.ProjectRequest.Type.Login)
                .setAgency(agencyDto)
                .build();
    }

    public static ProjectProto.ProjectRequest createSeeAllFlightsRequest(Agency agency) {
        ProjectProto.Agency agencyDto = ProjectProto.Agency.newBuilder()
                .setUsername(agency.getId()).setName(agency.getName()).setPassword(agency.getPassword()).build();

        return ProjectProto.ProjectRequest.newBuilder()
                .setType(ProjectProto.ProjectRequest.Type.GetAllFlights)
                .setAgency(agencyDto)
                .build();
    }

    public static ProjectProto.ProjectRequest createFilteredFlightsRequest(Flight flight) {
        LocalDateTime date = flight.getDepartureDateTime();
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(date.toEpochSecond(ZoneOffset.UTC))
                .setNanos(date.getNano())
                .build();
        ProjectProto.Flight flightDto = ProjectProto.Flight.newBuilder()
                .setDestination(flight.getDestination())
                .setDeparture(timestamp)
                .build();

        return ProjectProto.ProjectRequest.newBuilder()
                .setType(ProjectProto.ProjectRequest.Type.GetFilteredFlights)
                .setFlight(flightDto)
                .build();
    }

    public static ProjectProto.ProjectRequest numberOfSeatsRequest(Flight flight) {
        LocalDateTime date = flight.getDepartureDateTime();
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(date.toEpochSecond(ZoneOffset.UTC))
                .setNanos(date.getNano())
                .build();

        ProjectProto.Flight flightDto = ProjectProto.Flight.newBuilder()
                .setId(flight.getId())
                .setDestination(flight.getDestination())
                .setAirport(flight.getAirport())
                .setAvailableSeats(flight.getAvailableSeats())
                .setDeparture(timestamp)
                .setId(flight.getId())
                .build();

        return ProjectProto.ProjectRequest.newBuilder()
                .setType(ProjectProto.ProjectRequest.Type.GetNumberOfSeats)
                .setFlight(flightDto)
                .build();
    }

    public static ProjectProto.ProjectRequest bookTicket(Booking booking) {
        ProjectProto.Client client = ProjectProto.Client.newBuilder()
                .setName(booking.getClient().getName())
                .setAddress(booking.getClient().getAddress())
                .build();

        Flight flight = booking.getFlight();
        LocalDateTime date = flight.getDepartureDateTime();
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(date.toEpochSecond(ZoneOffset.UTC))
                .setNanos(date.getNano())
                .build();

        ProjectProto.Flight flightDto = ProjectProto.Flight.newBuilder()
                .setId(flight.getId())
                .setDestination(flight.getDestination())
                .setAirport(flight.getAirport())
                .setAvailableSeats(flight.getAvailableSeats())
                .setDeparture(timestamp)
                .setId(flight.getId())
                .build();

        ProjectProto.Booking bookingDto = ProjectProto.Booking.newBuilder()
                .setFlight(flightDto)
                .setClient(client)
                .addAllPassengers(booking.getPassengers())
                .build();

        return ProjectProto.ProjectRequest.newBuilder()
                .setType(ProjectProto.ProjectRequest.Type.BookTicket)
                .setBooking(bookingDto)
                .build();
    }

    public static Flight getFlight(ProjectProto.Flight flight) {
        LocalDateTime date = LocalDateTime.ofEpochSecond(flight.getDeparture().getSeconds(), flight.getDeparture().getNanos(), ZoneOffset.UTC);
        Flight fl = new Flight(flight.getDestination(), date, flight.getAirport(), flight.getAvailableSeats());
        fl.setId(flight.getId());
        return fl;
    }
}
