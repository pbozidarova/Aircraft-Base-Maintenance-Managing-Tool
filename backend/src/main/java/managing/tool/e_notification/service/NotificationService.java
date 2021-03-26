package managing.tool.e_notification.service;

import managing.tool.e_notification.model.dto.NotificationViewDto;

import java.util.Collection;
import java.util.List;


public interface NotificationService {

    List<NotificationViewDto> findAllIssues();
    List<NotificationViewDto> findAllIssuesByAuthor(String companyNum);
    List<NotificationViewDto> findAllIssuesByMaintenance(String maintenanceNum);

    List<NotificationViewDto> findAllNotifForTask(String taskNum);
}
