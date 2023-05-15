package org.example.protocol;

import org.example.*;
import org.example.dto.AgencyDto;
import org.example.dto.BookingDto;
import org.example.dto.DtoUtils;
import org.example.dto.FlightDto;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class ProjectServicesProxy implements IProjectServices {
    private String host;
    private int port;

    private IProjectObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> responses;
    private volatile boolean finished;

    public ProjectServicesProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses = new LinkedBlockingDeque<>();
    }

    @Override
    public void login(Agency agency, IProjectObserver client) throws ProjectException {
        initializeConnection();
        AgencyDto dto = DtoUtils.getDto(agency);
        Request request = new Request.Builder().type(RequestType.LOGIN).data(dto).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.OK) {
            this.client = client;
        } else {
            String error = response.data().toString();
            closeConnection();
            throw new ProjectException(error);
        }
    }

    @Override
    public void logout(Agency agency, IProjectObserver client) throws ProjectException {
        AgencyDto agencyDto = DtoUtils.getDto(agency);
        Request request = new Request.Builder().type(RequestType.LOGOUT).data(agencyDto).build();
        sendRequest(request);
        Response response = readResponse();
        closeConnection();
        if (response.type() == ResponseType.ERROR) {
            throw new ProjectException(response.data().toString());
        }
    }

    @Override
    public Flight[] getAllFlights(Agency agency, IProjectObserver client) throws ProjectException {
        AgencyDto agencyDto = DtoUtils.getDto(agency);
        Request request = new Request.Builder().type(RequestType.GET_ALL_FLIGHTS).data(agencyDto).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.FLIGHTS) {
            FlightDto[] flightDtos = (FlightDto[])response.data();

            return DtoUtils.fromDto(flightDtos);
        }
        throw new ProjectException("There was an error");
    }

    @Override
    public Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client) throws ProjectException {
        FlightDto flightDto = DtoUtils.getDto(flight);
        Request request = new Request.Builder().type(RequestType.GET_FILTERED_FLIGHTS).data(flightDto).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.FILTERED_FLIGHTS) {
            FlightDto[] flightDtos = (FlightDto[])response.data();

            return DtoUtils.fromDto(flightDtos);
        }
        throw new ProjectException("There was an error");
    }

    @Override
    public int getNumberOfSeats(Flight flight, IProjectObserver client) throws ProjectException {
        FlightDto flightDto = DtoUtils.getDto(flight);
        Request request = new Request.Builder().type(RequestType.GET_NUMBER_OF_SEATS).data(flightDto).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.NUMBER_SEATS) {
            return (int)response.data();
        }
        throw new ProjectException("There was an error");
    }

    @Override
    public void bookFlight(Booking booking, IProjectObserver client) throws ProjectException {
        BookingDto bookingDto = DtoUtils.getDto(booking);
        Request request = new Request.Builder().type(RequestType.BOOK_TICKET).data(bookingDto).build();
        sendRequest(request);
        Response response = readResponse();

        if (response.type() == ResponseType.ERROR) {
            throw new ProjectException(response.data().toString());
        }
    }

    private void closeConnection() {
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Request request) throws ProjectException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new ProjectException("Error sending object "+e);
        }
    }

    private Response readResponse() {
        Response response = null;
        try{
            response = responses.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() {
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread thread = new Thread(new ReaderThread());
        thread.start();
    }

    private class ReaderThread implements Runnable {
        @Override
        public void run() {
            while (!finished) {
                try {
                    Object response = input.readObject();
                    //logger.info("Response received");
                    if (isUpdate((Response) response)) {
                        handleUpdate((Response) response);
                    } else {
                        try {
                            responses.put((Response) response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void handleUpdate(Response response) {
        try {
            client.ticketBuyed();
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.BOOKING;
    }
}
