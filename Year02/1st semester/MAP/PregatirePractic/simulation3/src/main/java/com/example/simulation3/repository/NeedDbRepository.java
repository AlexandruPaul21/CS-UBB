package com.example.simulation3.repository;

import com.example.simulation3.domain.Need;
import com.example.simulation3.domain.Person;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NeedDbRepository implements Repository<Long, Need> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;

    public NeedDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    public List<Need> getNeedsAssigned(Person hero) {
        List<Need> needs = new ArrayList<>();
        String sql = "SELECT * FROM needs WHERE p_save = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, hero.getId());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long pNeed = resultSet.getLong("p_need");
                Long pSave = resultSet.getLong("p_save");
                String status = resultSet.getString("status");

                Need need = new Need(title, description,deadline, pNeed, pSave, status);
                need.setId(id);
                needs.add(need);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return needs;
    }

    public boolean markNeedAsSolved(Long id, Person hero) {
        String sql = "UPDATE needs SET p_save = ?, status = 'Erou gasit!' WHERE id = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, hero.getId());
            preparedStatement.setLong(2, id);

            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Need> getNeedAvbForHero(Person hero) {
        List<Need> needs = new ArrayList<>();
        String sql = "SELECT n.id, n.title, n.description, n.deadline, n.p_need, n.p_save, n.status FROM needs n " +
                "INNER JOIN persons p on p.id = n.p_need " +
                "WHERE n.status = 'Caut erou!' AND p.city = ? AND n.p_need <> ?";

        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, hero.getCity());
            preparedStatement.setLong(2, hero.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                LocalDateTime deadline = resultSet.getTimestamp("deadline").toLocalDateTime();
                Long pNeed = resultSet.getLong("p_need");
                Long pSave = resultSet.getLong("p_save");
                String status = resultSet.getString("status");

                Need need = new Need(title, description,deadline, pNeed, pSave, status);
                need.setId(id);
                needs.add(need);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return needs;
    }

    @Override
    public Need findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Need> findAll() {
        return null;
    }

    @Override
    public Need save(Need entity) {
        String sql = "INSERT INTO needs(id, title, description, deadline, p_need, p_save, status) " +
                "VALUES (?, ?, ?, ?, ?, null, ?)";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setLong(1, getLowestKey());
            preparedStatement.setString(2, entity.getTitle());
            preparedStatement.setString(3, entity.getDescription());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(entity.getDeadline()));
            preparedStatement.setLong(5, entity.getpInNeed());
            preparedStatement.setString(6, entity.getStatus());

            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Long getLowestKey() {
        String sql = "SELECT id FROM needs ORDER BY id DESC";
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
            return 1L;
        }
    }

    @Override
    public Need delete(Long aLong) {
        return null;
    }

    @Override
    public Need update(Need entity) {
        return null;
    }
}
