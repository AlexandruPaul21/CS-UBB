import org.example.repository.FlightDbRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryTest {
    @Test
    @DisplayName("Creation Test")
    public void testCreate() {
        FlightDbRepository flightDbRepository = new FlightDbRepository("jdbc:postgresql://localhost:5432/Simulare2", "postgres", "1234");
        assertEquals(flightDbRepository.getUrlDb(), "jdbc:postgresql://localhost:5432/Simulare2");
        assertEquals(flightDbRepository.getUsernameDb(), "postgres");
        assertEquals(flightDbRepository.getPasswordDb(), "1234");
    }

    @Test
    @DisplayName("Functional Test")
    public void testFunction() {
        FlightDbRepository flightDbRepository = new FlightDbRepository("jdbc:postgresql://localhost:5432/Simulare2", "postgres", "1234");
        assertEquals(flightDbRepository.getAllFrom(), List.of("Zalau"));
    }
}
