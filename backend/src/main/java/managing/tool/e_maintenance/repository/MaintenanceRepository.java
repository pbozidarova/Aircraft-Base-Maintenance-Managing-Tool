package managing.tool.e_maintenance.repository;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {
    MaintenanceEntity findByMaintenanceNum(String maintenanceNum);
    List<MaintenanceEntity> findAllByResponsibleEngineer(UserEntity userEntity);
    List<MaintenanceEntity> findAllByFacility(FacilityEntity facilityEntity);

    List<MaintenanceEntity> findAllByAircraft_AircraftRegistration(String registration);
    //List<MaintenanceEntity> findAllByTasksContaining(TaskEntity taskEntity);
}
