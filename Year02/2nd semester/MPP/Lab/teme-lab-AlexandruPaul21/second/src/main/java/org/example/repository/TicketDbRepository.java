package org.example.repository;

import org.example.domain.Ticket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class TicketDbRepository implements Repository<Long, Ticket> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public TicketDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Ticket findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public Ticket save(Ticket entity) {
        String sql = "INSERT INTO tickets(username, flight_id, purchase_time) VALUES " +
                "                                (?, ?, now())";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setLong(2, entity.getFlightId());

            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Ticket delete(Long aLong) {
        return null;
    }

    @Override
    public Ticket update(Ticket entity) {
        return null;
    }
}
