package managing.tool.e_maintenance.service;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.dto.MaintenanceViewModel;

import java.io.FileNotFoundException;
import java.util.List;

public interface MaintenanceService {

    List<MaintenanceViewModel> findAllMaintenanceEvents();
    MaintenanceViewModel findMaintenanceByNum(String maintenanceNum );

    void seedMaintenance() throws FileNotFoundException;
    boolean areEventsImported();

    MaintenanceEntity findByMaintenanceNum(String maintenanceNum);
}
