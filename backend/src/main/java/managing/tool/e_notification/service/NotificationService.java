package managing.tool.e_notification.service;

import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.model.dto.ReplyResponseDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;


public interface NotificationService {

    void generateMockupNotificationsOnInitialLaunch();
    boolean notificationsExist();

    List<NotificationViewDto> findAllNotifications();
    void evictCachedNotifications();
    void defineOverdueNotifications();
    List<NotificationViewDto> findAllNotificationsByAuthor(String companyNum);
    List<NotificationViewDto> findAllNotificationByMaintenance(String maintenanceNum);

    List<NotificationViewDto> findAllNotifForTask(String taskNum);

    Boolean notificationExists(String notificationNum);
    NotificationEntity findByNotificationNum(String notificationNum);
    Integer openNotificationsOfLoggedInUser(String jwt);
    NotificationViewDto updateNotification(String notificationNum, NotificationViewDto notificationViewDto);
    NotificationViewDto createNotification(NotificationViewDto notificationViewDto, String token);

    List<ReplyViewDto> getCommunication(String notificationNum);

    ReplyViewDto createReply(String notificationNum, String jwt, String reply, MultipartFile attachment) throws IOException;
}
