package managing.tool.e_notification.model.dto;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_notification.model.NotificationClassificationEnum;
import managing.tool.e_notification.model.NotificationStatusEnum;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;

import java.time.Instant;
import java.util.Set;

public class NotificationViewDto {
    private String notificationNum;
    private String author;
    private String status;
    private String classification;
    private String dueDate;
    private String maintenanceNum;
    private String taskNum;

    public NotificationViewDto() {
    }

    public String getNotificationNum() {
        return notificationNum;
    }

    public NotificationViewDto setNotificationNum(String notificationNum) {
        this.notificationNum = notificationNum;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public NotificationViewDto setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NotificationViewDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getClassification() {
        return classification;
    }

    public NotificationViewDto setClassification(String classification) {
        this.classification = classification;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public NotificationViewDto setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getMaintenanceNum() {
        return maintenanceNum;
    }

    public NotificationViewDto setMaintenanceNum(String maintenanceNum) {
        this.maintenanceNum = maintenanceNum;
        return this;
    }

    public String getTaskNum() {
        return taskNum;
    }

    public NotificationViewDto setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }
}
