package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.Agency;
import org.example.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class AgencyRepository implements IAgencyRepository {

    private final JdbcUtils jdbcUtils;
    private static final Logger logger = LogManager.getLogger();

    public AgencyRepository(Properties properties) {
        logger.info("Initialising Agency Repository with properties: {}", properties);
        jdbcUtils = new JdbcUtils(properties);
    }

    @Override
    public Agency findOne(String s) {
        logger.traceEntry();

        String sql = "SELECT * FROM agencies WHERE username = ?";
        Agency agency = null;
        try (
                Connection connection = jdbcUtils.getConnection()
                ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, s);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String username = resultSet.getString("username");
                String agName = resultSet.getString("ag_name");
                String password = resultSet.getString("pass");

                agency = new Agency(username, agName, password);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return agency;
    }

    @Override
    public Iterable<Agency> findAll() {
        logger.traceEntry();

        List<Agency> agencies = new ArrayList<>();

        String sql = "SELECT * FROM agencies";
        try (
                Connection connection = jdbcUtils.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String agName = resultSet.getString("ag_name");
                String password = resultSet.getString("pass");

                Agency agency = new Agency(username, agName, password);

                agencies.add(agency);
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return agencies;
    }

    @Override
    public Agency save(Agency entity) {
        logger.traceEntry();
        String sql = "INSERT INTO agencies VALUES (?, ?, ?)";

        try (
                Connection connection = jdbcUtils.getConnection()
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, entity.getId());
            preparedStatement.setString(2, entity.getName());
            preparedStatement.setString(3, entity.getPassword());

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
