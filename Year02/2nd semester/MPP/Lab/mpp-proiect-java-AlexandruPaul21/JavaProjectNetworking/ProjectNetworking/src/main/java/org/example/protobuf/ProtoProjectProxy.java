package org.example.protobuf;

import org.example.*;

import java.io.*;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProtoProjectProxy implements IProjectServices {
    private String host;
    private int port;

    private IProjectObserver client;

    private InputStream input;
    private OutputStream output;
    private Socket connection;

    private BlockingQueue<ProjectProto.ProjectResponse> responses;
    private volatile boolean finished;

    public ProtoProjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        responses =new LinkedBlockingQueue<>();
    }

    @Override
    public void login(Agency agency, IProjectObserver client) throws ProjectException {
        initializeConnection();
        sendRequest(ProtoUtils.createLoginRequest(agency));
        ProjectProto.ProjectResponse response = readResponse();

        if (response.getType() == ProjectProto.ProjectResponse.Type.Ok) {
            this.client = client;
        } else {
            String error = response.toString();
            closeConnection();
            throw new ProjectException(error);
        }
    }

    @Override
    public void logout(Agency agency, IProjectObserver client) throws ProjectException {
        sendRequest(ProtoUtils.createLogoutRequest(agency));
        ProjectProto.ProjectResponse response = readResponse();
        closeConnection();
        if (response.getType() == ProjectProto.ProjectResponse.Type.Error) {
            throw new ProjectException(response.toString());
        }
    }

    @Override
    public Flight[] getAllFlights(Agency agency, IProjectObserver client) throws ProjectException {
        sendRequest(ProtoUtils.createSeeAllFlightsRequest(agency));
        ProjectProto.ProjectResponse response = readResponse();

        if (response.getType() == ProjectProto.ProjectResponse.Type.Flights) {
            int size = response.getFlightsCount();

            Flight[] flights = new Flight[size];

            for (int index = 0; index < size; index++) {
                ProjectProto.Flight flight = response.getFlights(index);
                flights[index] = ProtoUtils.getFlight(flight);
            }

            return flights;
        }
        throw new ProjectException("There was an error");
    }

    @Override
    public Flight[] getFlightsForDestinationAndDate(Flight flight, IProjectObserver client) throws ProjectException {
        sendRequest(ProtoUtils.createFilteredFlightsRequest(flight));
        ProjectProto.ProjectResponse response = readResponse();

        if (response.getType() == ProjectProto.ProjectResponse.Type.FilteredFlights) {
            int size = response.getFlightsCount();

            Flight[] flights = new Flight[size];

            for (int index = 0; index < size; index++) {
                ProjectProto.Flight flight1 = response.getFlights(index);
                flights[index] = ProtoUtils.getFlight(flight1);
            }

            return flights;
        }
        throw new ProjectException("There was an error");
    }

    @Override
    public int getNumberOfSeats(Flight flight, IProjectObserver client) throws ProjectException {
        sendRequest(ProtoUtils.numberOfSeatsRequest(flight));
        ProjectProto.ProjectResponse response = readResponse();

        if (response.getType() == ProjectProto.ProjectResponse.Type.NumberOfSeats) {
            return response.getNumber();
        }
        throw new ProjectException("There was an error");
    }

    @Override
    public void bookFlight(Booking booking, IProjectObserver client) throws ProjectException {
        sendRequest(ProtoUtils.bookTicket(booking));
        ProjectProto.ProjectResponse response = readResponse();

        if (response.getType() == ProjectProto.ProjectResponse.Type.Error) {
            throw new ProjectException(response.toString());
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

    private void sendRequest(ProjectProto.ProjectRequest request) throws ProjectException {
        try {
            request.writeDelimitedTo(output);
            output.flush();
        } catch (IOException e) {
            throw new ProjectException("Error sending object "+e);
        }
    }

    private ProjectProto.ProjectResponse readResponse(){
        ProjectProto.ProjectResponse response=null;
        try{
            response = responses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }

    private void initializeConnection() {
        try {
            connection=new Socket(host,port);
            output=connection.getOutputStream();
            input=connection.getInputStream();
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReader() {
        Thread thread = new Thread(new ProtoProjectProxy.ReaderThread());
        thread.start();
    }

    private class ReaderThread implements Runnable {
        @Override
        public void run() {
            while (!finished) {
                try {
                    ProjectProto.ProjectResponse response = ProjectProto.ProjectResponse.parseDelimitedFrom(input);

                    if (isUpdate(response)) {
                        handleUpdate(response);
                    } else {
                        try {
                            responses.put(response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private void handleUpdate(ProjectProto.ProjectResponse response) {
        try {
            client.ticketBuyed();
        } catch (ProjectException e) {
            e.printStackTrace();
        }
    }

    private boolean isUpdate(ProjectProto.ProjectResponse response) {
        return response.getType() == ProjectProto.ProjectResponse.Type.Booking;
    }
}
