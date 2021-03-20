package managing.tool.e_notification.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "issues")
public class NotificationEntity extends BaseEntity {

    private String notificationNum;
    private Set<CommunicationEntity> communication;
    private UserEntity author;
    private NotificationStatusEnum status;
    private NotificationClassificationEnum classification;
    private LocalDateTime dueDate;

    private MaintenanceEntity maintenance;
    private Set<TaskEntity> tasks;

    public NotificationEntity() {
    }

    @Column(name = "notification_num", nullable = false, unique = true)
    public String getNotificationNum() {
        return notificationNum;
    }

    public NotificationEntity setNotificationNum(String notificationNum) {
        this.notificationNum = notificationNum;
        return this;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<CommunicationEntity> getCommunication() {
        return communication;
    }

    public NotificationEntity setCommunication(Set<CommunicationEntity> replay) {
        this.communication = replay;
        return this;
    }

    @OneToOne
    public UserEntity getAuthor() {
        return author;
    }

    public NotificationEntity setAuthor(UserEntity createdBy) {
        this.author = createdBy;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public NotificationStatusEnum getStatus() {
        return status;
    }

    public NotificationEntity setStatus(NotificationStatusEnum status) {
        this.status = status;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = false)
    public NotificationClassificationEnum getClassification() {
        return classification;
    }

    public NotificationEntity setClassification(NotificationClassificationEnum classification) {
        this.classification = classification;
        return this;
    }


    @Column(name = "due_date", nullable = false)
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public NotificationEntity setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @OneToOne
    public MaintenanceEntity getMaintenance() {
        return maintenance;
    }

    public NotificationEntity setMaintenance(MaintenanceEntity maintenance) {
        this.maintenance = maintenance;
        return this;
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "issues_tasks",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public NotificationEntity setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }
}
