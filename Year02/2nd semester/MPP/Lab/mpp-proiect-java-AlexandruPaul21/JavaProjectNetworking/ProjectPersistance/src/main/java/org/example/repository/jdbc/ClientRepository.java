package org.example.repository.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Client;
import org.example.repository.IClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ClientRepository implements IClientRepository {

    private final JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

    public ClientRepository(Properties properties) {
        logger.info("Initialising ClientRepository with properties {}", properties);
        jdbcUtils = new JdbcUtils(properties);
    }

    public Long getLowestAvbId() {
        String sql = "SELECT MAX(id) FROM clients";
        Long id = null;

        try (
                Connection connection = jdbcUtils.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public Client findOne(Long aLong) {
        logger.traceEntry();
        String sql = "SELECT * FROM clients WHERE id = ?";

        Client client = null;

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, aLong);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String clientName = resultSet.getString("client_name");
                String address = resultSet.getString("address");

                client = new Client(clientName, address);
                client.setId(aLong);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.traceExit();
        return client;
    }

    @Override
    public Iterable<Client> findAll() {
        logger.traceEntry();
        String sql = "SELECT * FROM clients";

        List<Client> clients = new ArrayList<>();

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String clientName = resultSet.getString("client_name");
                String address = resultSet.getString("address");

                Client client = new Client(clientName, address);
                client.setId(id);

                clients.add(client);
            }

        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }

        logger.traceExit();
        return clients;
    }

    @Override
    public Client save(Client entity) {
        String sql = "INSERT INTO clients VALUES (?, ?, ?)";

        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getAddress());

            preparedStatement.executeUpdate();
            entity = null;
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return entity;
    }
}
