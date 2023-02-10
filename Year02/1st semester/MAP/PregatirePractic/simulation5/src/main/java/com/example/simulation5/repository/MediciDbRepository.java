package com.example.simulation5.repository;

import com.example.simulation5.domain.Medic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class MediciDbRepository {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public MediciDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<Medic> getMedicForSectie(Long idSectie1) {
        List<Medic> medici = new ArrayList<>();
        String sql = "SELECT * FROM medici WHERE id_sectie = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setLong(1, idSectie1);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idSectie = resultSet.getLong("id_sectie");
                String nume = resultSet.getString("nume");
                int vechime = resultSet.getInt("vechime");
                boolean rezi = resultSet.getBoolean("rezident");

                Medic medic = new Medic(idSectie, nume,vechime, rezi);
                medic.setId(id);
                medici.add(medic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medici;
    }

    public List<Medic> findAll() {
        List<Medic> medici = new ArrayList<>();
        String sql = "SELECT * FROM medici";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idSectie = resultSet.getLong("id_sectie");
                String nume = resultSet.getString("nume");
                int vechime = resultSet.getInt("vechime");
                boolean rezi = resultSet.getBoolean("rezident");

                Medic medic = new Medic(idSectie, nume,vechime, rezi);
                medic.setId(id);
                medici.add(medic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medici;
    }
}
