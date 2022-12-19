import domain.Friendship;
import domain.User;
import domain.validators.FriendshipValidator;
import domain.validators.UserValidator;
import domain.validators.Validator;
import repository.db.DbFriendshipRepository;
import repository.db.DbUserRepository;
import repository.file.FileFriendshipRepository;
import repository.file.FileUserRepository;
import service.FriendshipService;
import service.UserService;
import ui.Console;

/**
 * The main class
 */
public class Main {
    public static void main(String[] args) {
        Validator<User> userValidator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();
        //FileUserRepository userRepository = new FileUserRepository(userValidator, "data/users.csv");
        //FileFriendshipRepository friendshipRepository = new FileFriendshipRepository(friendshipValidator, "data/friendships.csv");
        DbUserRepository userRepository = new DbUserRepository(userValidator,
                "jdbc:postgresql://localhost:5432/MAP_ToySocialNetwork","postgres","1234");
        DbFriendshipRepository friendshipRepository = new DbFriendshipRepository(friendshipValidator,
                "jdbc:postgresql://localhost:5432/MAP_ToySocialNetwork","postgres","1234");
        UserService userService = new UserService(userRepository);
        FriendshipService friendshipService = new FriendshipService(friendshipRepository);
        Console ui = new Console(userService, friendshipService);
        ui.printMenu();
    }
}