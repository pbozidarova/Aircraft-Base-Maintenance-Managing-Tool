package managing.tool.e_maintenance.repository;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {
    MaintenanceEntity findByMaintenanceNum(String maintenanceNum);
    List<MaintenanceEntity> findAllByResponsibleEngineer(UserEntity userEntity);
}
