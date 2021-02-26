package managing.tool.repository;

import managing.tool.model.entity.MaintenanceEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceEventRepository extends JpaRepository<MaintenanceEvent, Long> {
}
