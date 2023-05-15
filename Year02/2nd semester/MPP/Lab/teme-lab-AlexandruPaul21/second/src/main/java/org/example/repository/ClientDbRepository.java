package org.example.repository;

import org.example.domain.Client;

import java.sql.*;
import java.util.List;

public class ClientDbRepository implements Repository<String, Client> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public ClientDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Client findOne(String s) {
        Client client;
        String sql = "SELECT * FROM clients WHERE username = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, s);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("client_name");
            client = new Client(name);
            return client;
        } catch (SQLException e) {
            return null;
        }
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
    public Client delete(String s) {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }
}
