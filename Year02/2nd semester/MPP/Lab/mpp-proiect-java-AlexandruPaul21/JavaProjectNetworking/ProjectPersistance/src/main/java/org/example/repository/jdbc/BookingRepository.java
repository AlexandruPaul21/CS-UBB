package org.example.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Booking;
import org.example.Client;
import org.example.Flight;
import org.example.repository.IBookingRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class BookingRepository implements IBookingRepository {

    private final JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

    public BookingRepository(Properties properties) {
        logger.info("Initialised Booking repository with properties: {}", properties);
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Booking findOne(Long aLong) {
        logger.traceEntry();
        Booking booking = null;

        String sql = "SELECT * FROM bookings b " +
                "INNER JOIN flights f on f.id = b.id_flight " +
                "INNER JOIN clients c on c.id = b.id_client WHERE b.id = ?";

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                System.out.println(id);
                Long idClient = resultSet.getLong("id_client");
                String clientName = resultSet.getString("client_name");
                String address = resultSet.getString("address");
                Client client = new Client(clientName, address);
                client.setId(idClient);

                Long idFlight = resultSet.getLong("id_flight");
                String destination = resultSet.getString("destination");
                LocalDateTime departureDateTime = resultSet
                        .getTimestamp("departure_date_time").toLocalDateTime();
                String airport = resultSet.getString("airport");
                int availableSeats = resultSet.getInt("available_seats");
                Flight flight = new Flight(destination, departureDateTime, airport, availableSeats);
                flight.setId(idFlight);

                String passengersString = resultSet.getString("clients_name");

                List<String> passengers = Arrays.asList(passengersString.split(","));

                booking = new Booking(flight, client, passengers);
                booking.setId(id);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.traceExit();
        return booking;
    }

    @Override
    public Iterable<Booking> findAll() {
        logger.traceEntry();
        List<Booking> bookings = new ArrayList<>();

        String sql = "SELECT * FROM bookings b " +
                "INNER JOIN flights f on f.id = b.id_flight " +
                "INNER JOIN clients c on c.id = b.id_client";

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                System.out.println(id);
                Long idClient = resultSet.getLong("id_client");
                String clientName = resultSet.getString("client_name");
                String address = resultSet.getString("address");
                Client client = new Client(clientName, address);
                client.setId(idClient);

                Long idFlight = resultSet.getLong("id_flight");
                String destination = resultSet.getString("destination");
                LocalDateTime departureDateTime = resultSet
                        .getTimestamp("departure_date_time").toLocalDateTime();
                String airport = resultSet.getString("airport");
                int availableSeats = resultSet.getInt("available_seats");
                Flight flight = new Flight(destination, departureDateTime, airport, availableSeats);
                flight.setId(idFlight);

                String passengersString = resultSet.getString("clients_name");

                List<String> passengers = Arrays.asList(passengersString.split(","));

                Booking booking = new Booking(flight, client, passengers);
                booking.setId(id);

                bookings.add(booking);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.traceExit();
        return bookings;
    }

    public Long getLowestAvbId() {
        String sql = "SELECT MAX(id) FROM bookings";
        Long id = null;

        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Booking save(Booking entity) {
        logger.traceEntry();
        String sql = "INSERT INTO bookings VALUES (?, ?, ?, ?)";

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setLong(2, entity.getFlight().getId());
            preparedStatement.setLong(3, entity.getClient().getId());
            String clientsList = String.join(",", entity.getPassengers());
            preparedStatement.setString(4, clientsList);

            preparedStatement.executeUpdate();
            entity = null;
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.traceExit();
        return entity;
    }

    @Override
    public int getNumberOfBookingsForFlight(Long id) {
        logger.traceEntry();
        int numberOfBookings = 0;

        List<Booking> flights = (List<Booking>) findAll();

        for (Booking booking : flights) {
            if (booking.getFlight().getId().equals(id)) {
                numberOfBookings += booking.getPassengers().size() + 1;
            }
        }

        logger.traceExit();
        return numberOfBookings;
    }
}
