package repository.db;

import domain.Friendship;
import domain.validators.ArgumentException;
import domain.validators.Validator;
import repository.memory.MemoryFriendshipRepository;

import java.sql.*;
import java.time.LocalDate;

public class DbFriendshipRepository extends MemoryFriendshipRepository {
    private String urlDb;
    private String usernameDb;
    private String passwordDb;

    public DbFriendshipRepository(Validator<Friendship> validator, String urlDb, String usernameDb, String passwordDb) {
        super(validator);
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
        loadData();
    }

    @Override
    public Friendship save(Friendship entity) {
        Friendship friendship = super.save(entity);
        if (friendship == null) {
            String sql = "INSERT INTO friendships(id,id1,id2,start) VALUES(?,?,?,?)";
            try (
                    Connection connection = DriverManager.getConnection(urlDb,usernameDb,passwordDb);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ){
                preparedStatement.setLong(1, entity.getId());
                preparedStatement.setLong(2, entity.getId1());
                preparedStatement.setLong(3, entity.getId2());
                preparedStatement.setDate(4, Date.valueOf(entity.getFriendsFrom()));
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return friendship;
    }

    @Override
    public Long delete(Long id1, Long id2) {
        Long id = null;
        try {
            id = super.delete(id1,id2);
        } catch (ArgumentException argumentException) {
            argumentException.printStackTrace();
        }
        if (id != null) {
            String sql = "DELETE FROM friendships WHERE id = ?";
            try (
                    Connection connection = DriverManager.getConnection(urlDb,usernameDb,passwordDb);
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    ) {
                preparedStatement.setLong(1, id);
                preparedStatement.executeUpdate();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return id;
    }

    private void loadData() {
        try (
                Connection connection = DriverManager.getConnection(urlDb,usernameDb,passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM friendships");
                ResultSet resultSet = preparedStatement.executeQuery();
                ) {
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long id1 = resultSet.getLong("id1");
                Long id2 = resultSet.getLong("id2");
                LocalDate localDate = resultSet.getDate("start").toLocalDate();
                Friendship friendship = new Friendship(id1, id2);
                friendship.setId(id);
                friendship.setFriendsFrom(localDate);
                super.save(friendship);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
