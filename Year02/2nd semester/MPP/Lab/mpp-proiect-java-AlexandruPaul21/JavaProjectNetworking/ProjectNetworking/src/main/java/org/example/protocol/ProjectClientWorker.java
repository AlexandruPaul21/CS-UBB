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

public class ProjectClientWorker implements Runnable, IProjectObserver {
    private IProjectServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;

    public ProjectClientWorker(IProjectServices server, Socket connection) {
        this.server = server;
        this.connection = connection;

        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(connected){
            try {
                Object request = input.readObject();
                Response response = handleRequest((Request)request);
                if (response != null){
                    sendResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }

    private void sendResponse(Response response) throws IOException {
        output.writeObject(response);
        output.flush();
    }

    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request) {
        if (request.type() == RequestType.LOGIN) {
            AgencyDto dto = (AgencyDto)request.data();
            Agency agency = DtoUtils.fromDto(dto);
            try {
                server.login(agency, this);
                return okResponse;
            } catch (ProjectException e) {
                connected = false;
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.LOGOUT) {
            AgencyDto dto = (AgencyDto)request.data();
            Agency agency = DtoUtils.fromDto(dto);
            try {
                server.logout(agency, this);
                connected = false;
                return okResponse;
            } catch (ProjectException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_ALL_FLIGHTS) {
            AgencyDto dto = (AgencyDto)request.data();
            Agency agency = DtoUtils.fromDto(dto);
            try {
                Flight[] ans = server.getAllFlights(agency, this);
                FlightDto[] dtos = DtoUtils.getDto(ans);
                return new Response.Builder().type(ResponseType.FLIGHTS).data(dtos).build();
            } catch (ProjectException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_FILTERED_FLIGHTS) {
            FlightDto flightDto = (FlightDto)request.data();
            Flight flight = DtoUtils.fromDto(flightDto);

            try {
                Flight[] ans = server.getFlightsForDestinationAndDate(flight, this);
                FlightDto[] dtos = DtoUtils.getDto(ans);
                return new Response.Builder().type(ResponseType.FILTERED_FLIGHTS).data(dtos).build();
            } catch (ProjectException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.GET_NUMBER_OF_SEATS) {
            FlightDto flightDto = (FlightDto)request.data();
            Flight flight = DtoUtils.fromDto(flightDto);

            try {
                int ans = server.getNumberOfSeats(flight, this);
                return new Response.Builder().type(ResponseType.NUMBER_SEATS).data(ans).build();
            } catch (ProjectException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        if (request.type() == RequestType.BOOK_TICKET) {
            BookingDto bookingDto = (BookingDto)request.data();
            Booking booking = DtoUtils.fromDto(bookingDto);

            try {
                server.bookFlight(booking, this);
                return okResponse;
            } catch (ProjectException e) {
                return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
            }
        }

        return null;
    }

    @Override
    public void ticketBuyed() throws ProjectException {
        AgencyDto agencyDto = new AgencyDto("agency1", "agency1","1234");
        Response response = new Response.Builder().type(ResponseType.BOOKING).data(agencyDto).build();

        try {
            sendResponse(response);
        } catch (IOException e) {
            throw new ProjectException("Error sending response "+e);
        }
    }
}
