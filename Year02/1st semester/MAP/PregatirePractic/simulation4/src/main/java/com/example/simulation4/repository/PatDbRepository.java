package com.example.simulation4.repository;

import com.example.simulation4.domain.Pat;

import java.sql.*;
import java.util.List;

public class PatDbRepository implements Repository<Long, Pat> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public PatDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public int getNrOfocupat() {
        String sql = "SELECT COUNT(*) FROM paturi WHERE cnp_pacient is not null";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean interneaza(Long cnp, String tip) {
        int avb = getNrOfFree(tip);
        if (avb == 0) {
            return false;
        }

        String sql = "UPDATE paturi SET cnp_pacient = ? WHERE id = (SELECT id FROM paturi WHERE tip = ? LIMIT 1)";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, cnp);
            preparedStatement.setString(2, tip);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int getNrOfFree(String type) {
        String sql = "SELECT COUNT(*) FROM paturi WHERE tip = ? AND cnp_pacient is null";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, type);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Pat findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Pat> findAll() {
        return null;
    }

    @Override
    public Pat save(Pat entity) {
        return null;
    }

    @Override
    public Pat delete(Long aLong) {
        return null;
    }

    @Override
    public Pat update(Pat entity) {
        return null;
    }
}
