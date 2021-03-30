package managing.tool.e_notification.service;

import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;

import java.util.Collection;
import java.util.List;


public interface NotificationService {

    void generateMockupNotificationsOnInitialLaunch();
    boolean notificationsExist();

    List<NotificationViewDto> findAllNotifications();
    List<NotificationViewDto> findAllNotificationsByAuthor(String companyNum);
    List<NotificationViewDto> findAllNotificationByMaintenance(String maintenanceNum);

    List<NotificationViewDto> findAllNotifForTask(String taskNum);

    NotificationEntity findByNotificationNum(String notificationNum);
    Boolean notificationExists(String notificationNum);
    NotificationViewDto updateNotification(NotificationViewDto notificationViewDto);
    NotificationViewDto createNotification(NotificationViewDto notificationViewDto);

    List<ReplyViewDto> getCommunication(String notificationNum);
}
