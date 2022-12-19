package repository.db;

import domain.User;
import domain.validators.Validator;
import repository.memory.MemoryUserRepository;

import java.sql.*;

public class DbUserRepository extends MemoryUserRepository {
    private String urlDb;
    private String usernameDb;
    private String passwordDb;

    public DbUserRepository(Validator<User> validator, String urlDb, String usernameDb, String passwordDb) {
        super(validator);
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
        loadData();
    }

    @Override
    public User save(User entity) {
        User user = super.save(entity);
        if (user == null) {
            String sql = "INSERT INTO users(id,firstname,lastname,email,pass) VALUES (?,?,?,?,?)";
            try (
                    Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ) {
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setString(2, entity.getFirstname());
                preparedStatement.setString(3, entity.getLastname());
                preparedStatement.setString(4, entity.getEmail());
                preparedStatement.setString(5, entity.getPassword());
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User delete(Long aLong) {
        User user = super.delete(aLong);
        if (user != null) {
            String sql = "DELETE FROM users WHERE id = ?";
            try (
                    Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ) {
                preparedStatement.setLong(1, aLong);
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public User update(User entity) {
        User user = super.update(entity);
        if (user == null) {
            String sql = "UPDATE users SET " +
                    "firstname = ?," +
                    "lastname = ?," +
                    "email = ?," +
                    "pass = ? WHERE id = ?;";
            try (
                    Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ) {
                preparedStatement.setString(1, entity.getFirstname());
                preparedStatement.setString(2, entity.getLastname());
                preparedStatement.setString(3, entity.getEmail());
                preparedStatement.setString(4, entity.getPassword());
                preparedStatement.setLong(5, entity.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return user;
    }

    private void loadData() {
        try (
                Connection connection = DriverManager.getConnection(urlDb,usernameDb,passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
                ResultSet resultSet = preparedStatement.executeQuery();
                ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String pass = resultSet.getString("pass");
                User user = new User(firstname, lastname, email, pass);
                user.setId(id);
                super.save(user);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
