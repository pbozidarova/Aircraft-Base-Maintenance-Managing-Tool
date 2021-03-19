package managing.tool.e_maintenance.repository;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRepository extends JpaRepository<MaintenanceEntity, Long> {
    MaintenanceEntity findByMaintenanceNum(String maintenanceNum);
}
