package com.example.javafxproject.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private final Properties properties;
    private static final Logger logger = LogManager.getLogger();

    public JdbcUtils(Properties properties) {
        this.properties = properties;
    }

    private Connection connection = null;

    private Connection getNewConnection() {
        logger.traceEntry();

        String url = properties.getProperty("sql.url");
        String username = properties.getProperty("sql.username");
        String password = properties.getProperty("sql.password");

        logger.info("Trying to connect to url...{}", url);
        logger.info("Username...{}", username);
        logger.info("Password...{}", password);

        Connection con = null;
        try {
            if (username == null || password == null) {
                con = DriverManager.getConnection(url);
            } else {
                con = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException sqlException) {
            logger.error(sqlException);
            sqlException.printStackTrace();
        }
        return con;
    }

    public Connection getConnection() {
        logger.traceEntry();
        try {
            if (connection == null || connection.isClosed()) {
                connection = getNewConnection();
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
        logger.traceExit();
        return connection;
    }
}
