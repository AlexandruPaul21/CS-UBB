package com.example.simularemap.repository.dbRepo;

import com.example.simularemap.domain.Reservation;
import com.example.simularemap.repository.Repository;

import java.sql.*;
import java.util.List;

public class ReservationDbRepo implements Repository<Double, Reservation> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public ReservationDbRepo(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Reservation findOne(Double aDouble) {
        return null;
    }

    @Override
    public List<Reservation> findAll() {
        return null;
    }

    @Override
    public Reservation save(Reservation entity) {
        String sql = "INSERT INTO reservations(id, client_id, hotel_id, \"startDate\", \"noNights\") VALUES\n" +
                        "(?, ?, ?, ?, ?)";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setDouble(1, entity.getId());
            preparedStatement.setLong(2, entity.getClientId());
            preparedStatement.setDouble(3, entity.getHotelId());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getStartDate()));
            preparedStatement.setInt(5, entity.getNoNights());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    public Double getValidId() {
        double id;
        String sql = "SELECT id FROM reservations ORDER BY id DESC ";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getDouble("id") + 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public Reservation delete(Double aDouble) {
        return null;
    }

    @Override
    public Reservation update(Reservation entity) {
        return null;
    }
}
