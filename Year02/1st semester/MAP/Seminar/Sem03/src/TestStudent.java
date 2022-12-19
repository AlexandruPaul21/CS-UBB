import domain.Validator.StudentValidator;
import repository.StudentFileRepository;

public class TestStudent {
    public static void main(String[] args) {
        StudentFileRepository studentFileRepository = new StudentFileRepository(new StudentValidator(),"data/students.csv");
        studentFileRepository.findAll().forEach(System.out::println);
    }
}
