package managing.tool.e_task.repository;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
        List<TaskEntity> findAllByPreparedByContains(UserEntity user);

        List<TaskEntity> findAllByMaintenancesContains(MaintenanceEntity maintenance);
}
