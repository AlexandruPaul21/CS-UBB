package org.example;

import org.example.model.Flight;
import org.example.start.RestTest;
import org.example.start.ServiceException;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RestTest restTest = new RestTest();

        try {
            System.out.println(Arrays.toString(restTest.getAll()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(restTest.getById("1"));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(restTest.create(new Flight("WizzAir", LocalDateTime.of(2022, 10, 12, 12, 0), "Bucuresti", 100)));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        try {
            Flight flight = new Flight("WizzAir", LocalDateTime.of(2022, 10, 12, 12, 0), "Bucuresti", 100);
            flight.setId(1L);
            restTest.update(flight);

            System.out.println(Arrays.toString(restTest.getAll()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }

        try {
            restTest.delete("1");
            System.out.println(Arrays.toString(restTest.getAll()));
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}