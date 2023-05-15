package org.example.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server = null;

    public AbstractServer(int port) {
        System.out.println("Created server object on port: " + port);
        this.port = port;
    }

    public void start() throws ServerException {
        try {
            server = new ServerSocket(port);
            while (true) {
                System.out.println("Waiting for clients...");
                Socket client = server.accept();
                System.out.println("Client connected!");
                processRequest(client);
            }
        } catch (IOException e) {
            throw new ServerException("Starting server error ", e);
        } finally {
            stop();
        }
    }

    protected abstract void processRequest(Socket client);
    public void stop() throws ServerException{
        try {
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
