package managing.tool.e_task.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    private String taskNum;
    private String code;
    private String description;

    private boolean isToolingAvailable;
    private boolean isJobcardsPrepared;
    private boolean isQualityAssured;

    private Set<MaintenanceEntity> maintenances;
    private Set<UserEntity> preparedBy;

    private Set<NotificationEntity> notifications;

    public TaskEntity() {
    }

    @Column(name = "task_num", nullable = false, unique = true)
    public String getTaskNum() {
        return taskNum;
    }

    public TaskEntity setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    @Column(name = "code", nullable = false, length = 3)
    public String getCode() {
        return code;
    }

    public TaskEntity setCode(String code) {
        this.code = code;
        return this;
    }
    @Column(columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    public TaskEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    @Column(name = "is_tooling_available", nullable = false)
    public boolean isToolingAvailable() {
        return isToolingAvailable;
    }

    public TaskEntity setToolingAvailable(boolean toolingAvailable) {
        isToolingAvailable = toolingAvailable;
        return this;
    }

    @Column(name = "is_jobcards_preapred", nullable = false)
    public boolean isJobcardsPrepared() {
        return isJobcardsPrepared;
    }

    public TaskEntity setJobcardsPrepared(boolean jobcardsPrepared) {
        isJobcardsPrepared = jobcardsPrepared;
        return this;
    }

    @Column(name = "is_quality_assured", nullable = false)
    public boolean isQualityAssured() {
        return isQualityAssured;
    }

    public TaskEntity setQualityAssured(boolean qualityAssured) {
        isQualityAssured = qualityAssured;
        return this;
    }

    @ManyToMany(mappedBy = "tasks", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<MaintenanceEntity> getMaintenances() {
        return maintenances;
    }

    public TaskEntity setMaintenances(Set<MaintenanceEntity> maintenances) {
        this.maintenances = maintenances;
        return this;
    }

    @ManyToMany
    public Set<UserEntity> getPreparedBy() {
        return preparedBy;
    }

    public TaskEntity setPreparedBy(Set<UserEntity> preparedBy) {
        this.preparedBy = preparedBy;
        return this;
    }

    @OneToMany
    public Set<NotificationEntity> getNotifications() {
        return notifications;
    }

    public TaskEntity setNotifications(Set<NotificationEntity> notifications) {
        this.notifications = notifications;
        return this;
    }
}
