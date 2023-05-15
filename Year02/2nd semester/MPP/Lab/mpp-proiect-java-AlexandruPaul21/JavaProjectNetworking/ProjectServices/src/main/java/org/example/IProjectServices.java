package org.example;

public interface IProjectServices {
    void login(Agency agency, IProjectObserver client) throws ProjectException;
    void logout(Agency agency, IProjectObserver client) throws ProjectException;
    Flight[] getAllFlights(Agency agency, IProjectObserver client) throws ProjectException;
    Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client) throws ProjectException;
    int getNumberOfSeats(Flight flight, IProjectObserver client) throws ProjectException;
    void bookFlight(Booking booking, IProjectObserver client) throws ProjectException;
}
