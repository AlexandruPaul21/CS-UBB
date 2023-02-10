package com.example.simularemap.repository.dbRepo;

import com.example.simularemap.domain.Client;
import com.example.simularemap.domain.Dtos.OfferDTO;
import com.example.simularemap.domain.enums.HobbyType;
import com.example.simularemap.repository.Repository;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ClientsDbRepo implements Repository<Long, Client> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public ClientsDbRepo(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<OfferDTO> findByPercentage(int percentage) {
        List<OfferDTO> offers = new ArrayList<>();
        String sql = "SELECT h.\"hotelName\", l.\"locationName\", sp.start_date, sp.end_date FROM special_offers sp\n" +
                "INNER JOIN hotels h on h.\"hotelId\" = sp.hotel_id\n" +
                "INNER JOIN locations l on l.\"locationId\" = h.\"locationId\"\n" +
                "WHERE sp.percent < ? AND end_date >= CURRENT_DATE";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, percentage);

            ResultSet resultSet = preparedStatement.executeQuery();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            while (resultSet.next()) {
                String hotelName = resultSet.getString("hotelName");
                String locationName = resultSet.getString("locationName");
                String startDate = formatter.format(resultSet.getDate("start_date"));
                String endDate = formatter.format(resultSet.getDate("end_date"));
                OfferDTO offer = new OfferDTO(hotelName, locationName, startDate, endDate);
                offers.add(offer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return offers;
    }

    @Override
    public Client findOne(Long id) {
        Client client = null;
        String sql = "SELECT * FROM clients WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long idC = resultSet.getLong("id");
                String name = resultSet.getString("cl_name");
                int per = resultSet.getInt("fidelityGrade");
                int age = resultSet.getInt("age");
                String hob = resultSet.getString("hobby");
                client = new Client(name, per, age, HobbyType.MUSIC);
                client.setId(idC);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return client;
    }

    @Override
    public List<Client> findAll() {
        return null;
    }

    @Override
    public Client save(Client entity) {
        return null;
    }

    @Override
    public Client delete(Long aLong) {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }
}
