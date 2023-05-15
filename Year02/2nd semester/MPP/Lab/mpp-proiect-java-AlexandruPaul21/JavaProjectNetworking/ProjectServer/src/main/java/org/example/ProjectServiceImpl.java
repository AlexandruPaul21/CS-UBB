package org.example;

import org.example.repository.IAgencyRepository;
import org.example.repository.IBookingRepository;
import org.example.repository.IClientRepository;
import org.example.repository.IFlightRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProjectServiceImpl implements IProjectServices{
    private final IAgencyRepository agencyRepository;
    private final IBookingRepository bookingRepository;
    private final IClientRepository clientRepository;
    private final IFlightRepository flightRepository;

    private Map<String, IProjectObserver> loggedClients;

    public ProjectServiceImpl(IAgencyRepository agencyRepository, IBookingRepository bookingRepository, IClientRepository clientRepository, IFlightRepository flightRepository) {
        this.agencyRepository = agencyRepository;
        this.bookingRepository = bookingRepository;
        this.clientRepository = clientRepository;
        this.flightRepository = flightRepository;

        loggedClients = new ConcurrentHashMap<>();
    }

    @Override
    public synchronized void login(Agency agency, IProjectObserver client) throws ProjectException {
        boolean valid = agencyRepository.existsUser(agency.getId(), agency.getPassword());
        if (valid) {
            if (loggedClients.get(agency.getId()) != null) {
                throw new ProjectException("User already logged in!");
            }
            loggedClients.put(agency.getId(), client);
        } else {
            throw new ProjectException("Authentication failed!");
        }
    }

    @Override
    public synchronized void logout(Agency agency, IProjectObserver client) throws ProjectException {
        IProjectObserver localClient = loggedClients.remove(agency.getId());
        if (localClient == null) {
            throw new ProjectException("Agency " + agency.getId() + " was not logged in");
        }
    }

    @Override
    public Flight[] getAllFlights(Agency agency, IProjectObserver client) throws ProjectException {
        try {
            List<Flight> flights;
            flights = (List<Flight>) flightRepository.findAll();
            Flight[] flightsArray = new Flight[flights.size()];
            flightsArray = flights.toArray(flightsArray);

            return flightsArray;
        } catch (Exception e) {
            throw new ProjectException("Error " + e);
        }
    }

    @Override
    public Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client) throws ProjectException {
        try {
            List<Flight> flights;
            flights = (List<Flight>) flightRepository.findFlightByDestinationAndDate(flight.getDestination(), flight.getDepartureDateTime());
            Flight[] flightsArray = new Flight[flights.size()];
            flightsArray = flights.toArray(flightsArray);

            return flightsArray;
        } catch (Exception e) {
            throw new ProjectException("Error " + e);
        }
    }

    @Override
    public int getNumberOfSeats(Flight flight, IProjectObserver client) throws ProjectException {
        try {
            return bookingRepository.getNumberOfBookingsForFlight(flight.getId());
        } catch (Exception e) {
            throw new ProjectException("Error " + e);
        }
    }

    @Override
    public void bookFlight(Booking booking, IProjectObserver client) throws ProjectException {
        try {
            booking.getClient().setId(clientRepository.getLowestAvbId() + 1);
            clientRepository.save(booking.getClient());
            booking.setId(bookingRepository.getLowestAvbId() + 1);
            bookingRepository.save(booking);
            notifyAgencies();
        } catch (Exception e) {
            throw new ProjectException("Error " + e);
        }
    }

    private void notifyAgencies() {
        for (IProjectObserver client : loggedClients.values()) {
            try {
                client.ticketBuyed();
            } catch (ProjectException e) {
                System.err.println("Error notifying agency " + e);
            }
        }
    }
}
