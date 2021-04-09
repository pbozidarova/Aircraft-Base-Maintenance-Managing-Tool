package managing.tool.e_maintenance.event;

import lombok.AllArgsConstructor;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MaintenanceEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(String maintenanceNum) {
        MaintenanceEvent event = new MaintenanceEvent(maintenanceNum);
        applicationEventPublisher.publishEvent(event);
    }

}
