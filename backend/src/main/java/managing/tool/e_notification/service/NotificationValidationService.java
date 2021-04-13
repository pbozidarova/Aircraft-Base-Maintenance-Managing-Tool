package managing.tool.e_notification.service;

public interface NotificationValidationService {
    Boolean notificationExists(String notificationNum);

    void validateStatus(String status);
    void validateClassification(String classification);
    void validateIfMaintenanceExists(String maintenanceNum);
    void validateIfTaskExists(String taskNum);
}
