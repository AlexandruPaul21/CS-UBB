package com.example.simulation4.repository;

import com.example.simulation4.domain.Pacient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacientDbRepository implements Repository<String, Pacient> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public PacientDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<Pacient> findInNeed() {
        List<Pacient> pacients = new ArrayList<>();
        String sql = "SELECT * FROM pacienti WHERE cnp <> ANY(SELECT cnp_pacient FROM paturi)" +
                " ORDER BY gravitate DESC";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long cnp = resultSet.getLong("cnp");
                int varsta = resultSet.getInt("varsta");
                boolean pre = resultSet.getBoolean("prematur");
                String diag = resultSet.getString("diagnostic");
                int gravity = resultSet.getInt("gravitate");

                Pacient pacient = new Pacient(cnp, varsta, pre, diag, gravity);
                pacients.add(pacient);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pacients;
    }

    @Override
    public Pacient findOne(String s) {
        return null;
    }

    @Override
    public List<Pacient> findAll() {
        return null;
    }

    @Override
    public Pacient save(Pacient entity) {
        return null;
    }

    @Override
    public Pacient delete(String s) {
        return null;
    }

    @Override
    public Pacient update(Pacient entity) {
        return null;
    }
}
