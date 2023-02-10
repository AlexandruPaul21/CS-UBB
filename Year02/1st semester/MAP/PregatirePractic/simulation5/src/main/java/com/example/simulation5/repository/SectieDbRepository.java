package com.example.simulation5.repository;

import com.example.simulation5.domain.Sectie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectieDbRepository {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public SectieDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<Sectie> findAll() {
        List<Sectie> sectii = new ArrayList<>();
        String sql = "SELECT * FROM sectii";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idSef = resultSet.getLong("id_sef");
                Double pret = (double)resultSet.getFloat("pret");
                int durata = resultSet.getInt("durata");
                String nume = resultSet.getString("nume");

                Sectie sectie = new Sectie(nume, idSef, pret, durata);
                sectie.setId(id);
                sectii.add(sectie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectii;
    }

    public Sectie findOne(Long id) {
        Sectie sectie = null;
        String sql = "SELECT * FROM sectii WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long idSef = resultSet.getLong("id_sef");
                double pret = resultSet.getFloat("pret");
                int durata = resultSet.getInt("durata");
                String nume = resultSet.getString("nume");

                sectie = new Sectie(nume, idSef, pret, durata);
                sectie.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sectie;
    }
}
