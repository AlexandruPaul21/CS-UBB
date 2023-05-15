import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import repository.ComputerRepairRequestRepository;
import repository.ComputerRepairedFormRepository;
import repository.file.ComputerRepairedFormFileRepository;
import repository.jdbc.ComputerRepairRequestJdbcRepository;
import repository.jdbc.ComputerRepairedFormJdbcRepository;
import services.ComputerRepairServices;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepairShopConfig {
    @Bean
    Properties getProps() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("./bd.config.properties"));
        } catch (IOException e) {
            System.out.println("Cannot find bd.config " + e);
        }
        return properties;
    }

    @Bean
    ComputerRepairRequestRepository requestsRepo(){
        return new ComputerRepairRequestJdbcRepository(getProps());
    }

    @Bean
    ComputerRepairedFormRepository formsRepo(){
        return new ComputerRepairedFormJdbcRepository(getProps());
    }

    @Bean
    ComputerRepairServices services(){
        return new ComputerRepairServices(requestsRepo(),formsRepo());
    }

}
