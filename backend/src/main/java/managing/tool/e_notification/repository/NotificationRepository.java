package managing.tool.e_notification.repository;

import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, Long> {

    NotificationEntity findByNotificationNum(String notificationNum);

    List<NotificationEntity> findAllByAuthor(UserEntity userEntity);
    List<NotificationEntity> findAllByMaintenance(MaintenanceEntity maintenanceEntity);
    List<NotificationEntity> findAllByTask(TaskEntity taskEntity);
}
