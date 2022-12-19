package ui;

import domain.Friendship;
import domain.User;
import domain.validators.ArgumentException;
import domain.validators.ValidationException;
import service.FriendshipService;
import service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The console user interface
 */
public class Console {
    private UserService userService;
    private FriendshipService friendshipService;

    public Console(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    /**
     * Print menu function
     */
    public void printMenu() {
        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            System.out.println("-------------Welcome-------------");
            System.out.println("Choose one option by typing its digit and then enter");
            System.out.println("1.Add User");
            System.out.println("2.Delete User");
            System.out.println("3.Update User");
            System.out.println("4.Add friendship");
            System.out.println("5.Remove friendship");
            System.out.println("6.Find one friendship");
            System.out.println("9.List all users");
            System.out.println("10. List all friendships");
            System.out.println("0.Exit");
            System.out.print("Option: ");
            String optionAsString = scanner.next();
            scanner.nextLine();
            int option;

            try {
                option = Integer.parseInt(optionAsString);
            } catch (NumberFormatException ex) {
                option = -1;
            }

            if (option == 0) {
                exit = true;
            } else if (option == 1) {
                System.out.println("Introduce User attributes");

                String firstname, lastname, email, password;

                System.out.print("Firstname: ");
                firstname = scanner.nextLine();

                System.out.print("Lastname: ");
                lastname = scanner.nextLine();

                System.out.print("E-mail: ");
                email = scanner.nextLine();

                System.out.print("Password: ");
                password = scanner.nextLine();

                try {
                    userService.add(firstname, lastname, email, password);
                    System.out.println("User added successfully");
                } catch (ArgumentException | ValidationException ex) {
                    System.out.println(ex.getMessage());
                }

            } else if (option == 2) {
                System.out.print("Enter the ID you want to delete: ");
                long id = scanner.nextLong();
                try {
                    userService.delete(id);
                    System.out.println("Deleted successfully");
                } catch (ArgumentException ex) {
                    System.out.println(ex.getMessage());
                }

            } else if (option == 4) {
                System.out.print("Enter the Id of the first user: ");
                Long id1 = scanner.nextLong();
                System.out.print("Enter the Id of the second user: ");
                Long id2 = scanner.nextLong();

                try {
                    friendshipService.addFriend(id1, id2);
                    System.out.println("Friendship created");
                } catch(ArgumentException ae) {
                    System.out.println(ae.getMessage());
                }

            } else if (option == 5) {
                System.out.print("Enter the Id of the first user: ");
                Long id1 = scanner.nextLong();
                System.out.print("Enter the Id of the second user: ");
                Long id2 = scanner.nextLong();

                try {
                    friendshipService.removeFriend(id1, id2);
                    System.out.println("Friendship deleted");
                } catch (ArgumentException ae) {
                    System.out.println(ae.getMessage());
                }

            } else if (option == 3) {
                System.out.println("Enter the id of the user: ");
                Long id = scanner.nextLong();
                scanner.nextLine();

                System.out.println("Introduce User attributes");

                String firstname, lastname, email, password;

                System.out.print("Firstname: ");
                firstname = scanner.nextLine();

                System.out.print("Lastname: ");
                lastname = scanner.nextLine();

                System.out.print("E-mail: ");
                email = scanner.nextLine();

                System.out.print("Password: ");
                password = scanner.nextLine();

                try {
                    userService.update(id, firstname, lastname, email, password);
                    System.out.println("User updated successfully");
                } catch (ArgumentException | ValidationException ex) {
                    System.out.println(ex.getMessage());
                }

            } else if (option == 6) {
                System.out.println("Enter the id of the friendship: ");
                Long id = scanner.nextLong();
                Friendship friendship = friendshipService.findOne(id);

                System.out.println(friendship);

            } else if (option == 9) {
                System.out.println(userService.findAll().toString());
            } else if (option == 10) {
                System.out.println(friendshipService.findAll().toString());
            } else {
                System.out.println("The option is not implemented yet\n");
            }
        }
    }
}
