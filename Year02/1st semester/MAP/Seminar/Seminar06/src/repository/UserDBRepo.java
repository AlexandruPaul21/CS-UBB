package repository;

import domain.User;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class UserDBRepo implements  Repository<Long, User>{
    private String urlDb;
    private String usernameDb;
    private String passwordDb;

    public UserDBRepo(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public User findOne(Long aLong) {
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> userSet = new HashSet<>();
        try(Connection connection = DriverManager.getConnection(urlDb, usernameDb,passwordDb);
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
            ResultSet resultSet = preparedStatement.executeQuery();) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String lastName = resultSet.getString("last_name");
                String firstName = resultSet.getString("first_name");
                User user = new User(firstName, lastName);
                user.setId(id);
                userSet.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userSet;
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users(first_name, last_name) VALUES(?,?)";
        try (Connection connection = DriverManager.getConnection(urlDb, usernameDb,passwordDb);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User delete(Long aLong) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }
}
