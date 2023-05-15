package org.example;

import org.example.repository.FlightDbRepository;

public class Main {
    public static void main(String[] args) {
        FlightDbRepository flightDbRepository = new FlightDbRepository("jdbc:postgresql://localhost:5432/Simulare2", "postgres", "1234");
        System.out.println(flightDbRepository.getAllFrom());
    }
}