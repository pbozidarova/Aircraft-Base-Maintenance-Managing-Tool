package managing.tool.e_maintenance.service;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;

import java.util.List;

public interface MaintenanceService {
    MaintenanceViewDto updateMaintenance(MaintenanceViewDto maintenanceDataForUpdate, String jwt);
    MaintenanceViewDto createMaintenance(MaintenanceViewDto maintenanceNew, String jwt);

    List<MaintenanceViewDto> findAllMaintenanceEvents();
    MaintenanceViewDto findMaintenanceByNum(String maintenanceNum );
    List<MaintenanceViewDto> findAllMaintenanceByResponsibleEngineer(String companyNum );

    MaintenanceEntity findByMaintenanceNum(String maintenanceNum);

    Boolean maintenanceExists(String maintenanceNum);


}
