package com.example.simulation5.repository;

import com.example.simulation5.domain.Consultatie;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

public class ConsultatiiDbRepository {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public ConsultatiiDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<Consultatie> findAllForMed(Long idMedic) {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT * FROM consultatii WHERE id_medic = ? ORDER BY data ASC, ora ASC";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                ) {
            preparedStatement.setLong(1, idMedic);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id =  resultSet.getLong("id");
                Long cnpPac = resultSet.getLong("cnp_pacient");
                String numePac = resultSet.getString("nume_pacient");
                Date data = resultSet.getDate("data");
                int ora = resultSet.getInt("ora");

                Consultatie consultatie = new Consultatie(idMedic, cnpPac, numePac, data, ora);
                consultatii.add(consultatie);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return consultatii;
    }

    public Long getLowestFreeId() {
        String sql = "SELECT id FROM consultatii ORDER BY id DESC";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("id") + 1;
            } else {
                return 1L;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public boolean save(Consultatie consultatie) {
        Date date = consultatie.getData();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString  = formatter.format(date);
        String sql = "INSERT INTO consultatii(id, id_medic, cnp_pacient, nume_pacient, data, ora) VALUES " +
                "(?, ?, ?, ?, \'"+ dateString +"\', ?)";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, getLowestFreeId());
            preparedStatement.setLong(2, consultatie.getIdMedic());
            preparedStatement.setLong(3, consultatie.getCnpPacient());
            preparedStatement.setString(4, consultatie.getNumePacient());
            preparedStatement.setInt(5, consultatie.getOra());

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
