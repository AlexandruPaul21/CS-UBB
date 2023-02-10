package com.example.simulation2.repository;

import com.example.simulation2.domain.Flight;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightDbRepository implements Repository<Long, Flight> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public FlightDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<String> getAllFrom() {
        List<String> locations = new ArrayList<>();
        String sql = "SELECT DISTINCT f_from FROM flights";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String loc = resultSet.getString("f_from");
                locations.add(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public void book(Long flightId) {
        String sql = "UPDATE flights SET booked = booked + 1 WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, flightId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getAllTo() {
        List<String> locations = new ArrayList<>();
        String sql = "SELECT DISTINCT f_to FROM flights";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String loc = resultSet.getString("f_to");
                locations.add(loc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return locations;
    }

    public List<Flight> getFLightsForRoute(String from, String to, Date date) {
        List<Flight> flights = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date1 = formatter.format(date);
        String sql = "SELECT * FROM flights WHERE f_to = ? AND f_from = ? AND DATE(departure_time) = \'" + date1 +"\'";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, to);
            preparedStatement.setString(2, from);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                LocalDateTime departure = resultSet.getTimestamp("departure_time").toLocalDateTime();
                LocalDateTime arrival = resultSet.getTimestamp("landing_time").toLocalDateTime();
                int seats = resultSet.getInt("seats");
                int booked = resultSet.getInt("booked");

                Flight flight = new Flight(from, to, departure, arrival, seats, booked);
                flight.setId(id);
                flights.add(flight);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return flights;
    }

    @Override
    public Flight findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Flight> findAll() {
        return null;
    }

    @Override
    public Flight save(Flight entity) {
        return null;
    }

    @Override
    public Flight delete(Long aLong) {
        return null;
    }

    @Override
    public Flight update(Flight entity) {
        return null;
    }
}
