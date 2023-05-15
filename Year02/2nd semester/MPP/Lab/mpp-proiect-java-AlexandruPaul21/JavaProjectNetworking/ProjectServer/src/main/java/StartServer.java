import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.IProjectServices;
import org.example.ProjectServiceImpl;
import org.example.repository.IAgencyRepository;
import org.example.repository.IBookingRepository;
import org.example.repository.IClientRepository;
import org.example.repository.IFlightRepository;
import org.example.repository.jdbc.AgencyRepository;
import org.example.repository.jdbc.BookingRepository;
import org.example.repository.jdbc.ClientRepository;
import org.example.repository.jdbc.FlightRepository;
import org.example.repository.orm.AgencyOrmRepository;
import org.example.utils.AbstractServer;
import org.example.utils.ProjectConcurrentServer;
import org.example.utils.ServerException;

import java.io.IOException;
import java.util.Properties;

public class StartServer {
    private static final Logger logger = LogManager.getLogger();
    private static final int defaultPort = 55555;

    public static void main(String[] args) {
        logger.traceEntry();
        Properties serverProperties = new Properties();

        try {
            serverProperties.load(StartServer.class.getResourceAsStream("/project_server.properties"));
        } catch (IOException e) {
            logger.error(e);
            return;
        }

        IAgencyRepository agencyRepository = new AgencyOrmRepository();
        IBookingRepository bookingRepository = new BookingRepository(serverProperties);
        IClientRepository clientRepository = new ClientRepository(serverProperties);
        IFlightRepository flightRepository = new FlightRepository(serverProperties);

        IProjectServices projectServices = new ProjectServiceImpl(agencyRepository, bookingRepository, clientRepository, flightRepository);

        int projectServerPort = defaultPort;
        try {
            projectServerPort = Integer.parseInt(serverProperties.getProperty("project.server.port"));
        } catch (NumberFormatException nef) {
            logger.error(nef);
            logger.info("Using default port: {}", defaultPort);
        }

        logger.info("Starting server on port: {}", projectServerPort);

        AbstractServer server = new ProjectConcurrentServer(projectServerPort, projectServices);

        try {
            server.start();
        } catch (ServerException e) {
            logger.error(e);
        } finally {
            try {
                server.stop();
            } catch (ServerException e) {
                logger.error(e);
            }
        }

    }
}