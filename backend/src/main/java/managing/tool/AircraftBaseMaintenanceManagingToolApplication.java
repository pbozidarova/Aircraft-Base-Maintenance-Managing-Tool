package managing.tool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
@SpringBootApplication
public class AircraftBaseMaintenanceManagingToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(AircraftBaseMaintenanceManagingToolApplication.class, args);
    }

}
