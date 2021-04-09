package managing.tool.e_maintenance.event;

import org.springframework.context.ApplicationEvent;

public class MaintenanceEvent extends ApplicationEvent {

    private final String maintenanceNum;

    public MaintenanceEvent(String maintenanceNum) {
        super(maintenanceNum);
        this.maintenanceNum = maintenanceNum;
    }

    public String getMaintenanceNum() {
        return maintenanceNum;
    }
}
