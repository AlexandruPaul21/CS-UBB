import org.example.model.Booking;
import org.example.model.Client;
import org.example.model.Flight;
import org.example.repository.AgencyRepository;
import org.example.repository.BookingRepository;
import org.example.repository.ClientRepository;
import org.example.repository.FlightRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class RepositoryTest {
    @Test
    @DisplayName("Test Agency")
    public void testAgency() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("./bd.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        AgencyRepository agencyRepository = new AgencyRepository(properties);
        System.out.println(agencyRepository.findAll());
    }

    @Test
    @DisplayName("Test Client")
    public void testClient() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("./bd.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        ClientRepository clientRepository = new ClientRepository(properties);
        System.out.println(clientRepository.findAll());
    }

    @Test
    @DisplayName("Test Flight")
    public void testFLight() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("./bd.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        FlightRepository flightRepository = new FlightRepository(properties);
        System.out.println(flightRepository.findAll());
        System.out.println(flightRepository.findOne(1L));
    }

    @Test
    @DisplayName("Test Booking")
    public void testBooking() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("./bd.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        BookingRepository bookingRepository = new BookingRepository(properties);
        System.out.println(bookingRepository.findAll());
        System.out.println(bookingRepository.findOne(2L));
    }
}
