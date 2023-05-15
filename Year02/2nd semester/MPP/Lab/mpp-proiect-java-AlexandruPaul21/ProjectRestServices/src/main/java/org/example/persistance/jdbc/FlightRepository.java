package org.example.persistance.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Flight;
import org.example.persistance.IFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Repository
public class FlightRepository implements IFlightRepository {

    private JdbcUtils jdbcUtils;

    @Autowired
    public void setJdbcUtils(JdbcUtils jdbcUtils) {
        this.jdbcUtils = jdbcUtils;
    }

    private static final Logger logger = LogManager.getLogger();

    public FlightRepository() {
        logger.info("Initialising FlightRepository");
    }

    public FlightRepository(Properties properties) {
        logger.info("Initialising FlightRepository with properties: {}", properties);
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Flight findOne(Long aLong) {
        logger.traceEntry();
        String sql = "SELECT * FROM flights WHERE id = ?";
        Flight flight = null;

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String destination = resultSet.getString("destination");
                LocalDateTime departureDateTime = resultSet
                        .getTimestamp("departure_date_time").toLocalDateTime();
                String airport = resultSet.getString("airport");
                int availableSeats = resultSet.getInt("available_seats");

                flight = new Flight(destination, departureDateTime, airport, availableSeats);
                flight.setId(id);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.traceExit();
        return flight;
    }

    @Override
    public Iterable<Flight> findAll() {
        logger.traceEntry();
        String sql = "SELECT * FROM flights";

        List<Flight> flights = new ArrayList<>();

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String destination = resultSet.getString("destination");
                LocalDateTime departureDateTime = resultSet
                        .getTimestamp("departure_date_time").toLocalDateTime();
                String airport = resultSet.getString("airport");
                int availableSeats = resultSet.getInt("available_seats");

                Flight flight = new Flight(destination, departureDateTime, airport, availableSeats);
                flight.setId(id);

                flights.add(flight);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return flights;
    }

    public Flight delete(Long id) {
        String sql = "DELETE FROM flights WHERE id = ?";
        Flight flight = findOne(id);
        try (
                Connection connection = jdbcUtils.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

            return flight;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Flight update(Flight flight) {
        String sql = "UPDATE flights SET destination = ?, departure_date_time = ?, airport = ?, available_seats = ? WHERE id = ?";
        try (
                Connection connection = jdbcUtils.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, flight.getDestination());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDateTime()));
            preparedStatement.setString(3, flight.getAirport());
            preparedStatement.setInt(4, flight.getAvailableSeats());
            preparedStatement.setLong(5, flight.getId());

            preparedStatement.executeUpdate();
            return flight;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Long getLowestAvbId() {
        String sql = "SELECT MAX(id) FROM flights";
        try (
                Connection connection = jdbcUtils.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong(1) + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 1L;
    }

    public Flight save(Flight flight) {
        flight.setId(getLowestAvbId());
        String sql = "INSERT INTO flights VALUES (?, ?, ?, ?, ?)";
        try (
                Connection connection = jdbcUtils.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, flight.getId());
            preparedStatement.setString(2, flight.getDestination());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(flight.getDepartureDateTime()));
            preparedStatement.setString(4, flight.getAirport());
            preparedStatement.setInt(5, flight.getAvailableSeats());

            preparedStatement.executeUpdate();
            return flight;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Iterable<Flight> findFlightByDestinationAndDate(String destination, LocalDateTime dateTime) {
        logger.traceEntry("findFlightByDestinationAndDate: destination = {}, dateTime = {}", destination, dateTime);
        LocalDateTime start = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 0, 0);
        LocalDateTime end = LocalDateTime.of(dateTime.getYear(), dateTime.getMonth(), dateTime.getDayOfMonth(), 23, 59);
        String sql = "SELECT * FROM flights WHERE destination = ? AND departure_date_time BETWEEN ? AND ?";

        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setString(1, destination);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(start));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(end));

            ResultSet resultSet = preparedStatement.executeQuery();

            List<Flight> flights = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String destination1 = resultSet.getString("destination");
                LocalDateTime departureDateTime = resultSet
                        .getTimestamp("departure_date_time").toLocalDateTime();
                String airport = resultSet.getString("airport");
                int availableSeats = resultSet.getInt("available_seats");

                Flight flight = new Flight(destination1, departureDateTime, airport, availableSeats);
                flight.setId(id);

                flights.add(flight);
            }
            logger.traceExit();
            return flights;
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return null;
    }
}
