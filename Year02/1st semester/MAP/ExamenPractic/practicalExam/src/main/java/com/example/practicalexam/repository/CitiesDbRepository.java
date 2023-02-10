package com.example.practicalexam.repository;

import com.example.practicalexam.domain.City;

import java.sql.*;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class CitiesDbRepository {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public CitiesDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<City> findAll() {
        List<City> cities = new ArrayList<>();
        String sql = "SELECT * FROM cities";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                City city = new City(name);
                city.setId(id);
                cities.add(city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public City findOne(Integer id) {
        City city = null;
        String sql = "SELECT * FROM cities WHERE id = ?";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                city = new City(name);
                city.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return city;
    }
}
