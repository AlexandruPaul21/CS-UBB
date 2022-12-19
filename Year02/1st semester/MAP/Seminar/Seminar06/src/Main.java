import repository.UserDBRepo;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/Academic";
        UserDBRepo userDBRepo = new UserDBRepo(url, "postgres", "1234");

        userDBRepo.findAll().forEach(System.out::println);
    }
}