package managing.tool.e_maintenance.service;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.dto.MaintenanceRequestDto;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;

import java.util.List;

public interface MaintenanceService {
    MaintenanceViewDto updateMaintenance(MaintenanceRequestDto maintenanceDataForUpdate, String jwt);
    MaintenanceViewDto createMaintenance(MaintenanceRequestDto maintenanceNew, String jwt);
    void allocateRandomTasksToMaintenance(String maintenanceNum);
    void recalculateMaintenanceStatus();

    List<MaintenanceViewDto> findAllMaintenanceEvents();
    void evictCachedMaintenance();
    MaintenanceViewDto findMaintenanceByNum(String maintenanceNum );

    MaintenanceEntity findByMaintenanceNum(String maintenanceNum);
    MaintenanceEntity getRandomMaintenance();

    Boolean maintenanceExists(String maintenanceNum);

    //HATEOAS methods
    List<MaintenanceViewDto> findAllByFacility(String name);
    List<MaintenanceViewDto> findAllMaintenanceByResponsibleEngineer(String companyNum );

    List<MaintenanceViewDto> findAllByAircraft(String registration);

}
