package com.example.simulation3.repository;

import com.example.simulation3.domain.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDbRepository implements Repository<Long, Person> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public PersonDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Person findOne(Long aLong) {
        return null;
    }

    public List<String> findUsernames() {
        String sql = "SELECT username FROm persons";
        List<String> usernames = new ArrayList<>();
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String user = resultSet.getString("username");
                usernames.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    public Person findByUserAndPass(String username) {
        String sql = "SELECT * FROM persons " +
                "WHERE username = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Person person;
                Long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String user = resultSet.getString("username");
                String password = resultSet.getString("pass");
                String city = resultSet.getString("city");
                String street = resultSet.getString("street");
                String streetNo = resultSet.getString("noStreet");
                String phone = resultSet.getString("phone");

                person = new Person(firstname, lastname, user, password, city, street, streetNo, phone);
                person.setId(id);
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public List<Person> findAll() {
        return null;
    }

    @Override
    public Person save(Person entity) {
        String sql = "INSERT INTO persons(firstname, lastname, username, pass, city, street, \"noStreet\", phone)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, entity.getFirstname());
            preparedStatement.setString(2, entity.getLastname());
            preparedStatement.setString(3, entity.getUsername());
            preparedStatement.setString(4, entity.getPassword());
            preparedStatement.setString(5, entity.getCity());
            preparedStatement.setString(6, entity.getStreet());
            preparedStatement.setString(7, entity.getStreetNo());
            preparedStatement.setString(8, entity.getPhone());

            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Person delete(Long aLong) {
        return null;
    }

    @Override
    public Person update(Person entity) {
        return null;
    }
}
