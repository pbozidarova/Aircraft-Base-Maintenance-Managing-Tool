package managing.tool.e_notification.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "notifications")
public class NotificationEntity extends BaseEntity {

    private String notificationNum;
    private Set<ReplyEntity> communication;
    private UserEntity author;
    private NotificationStatusEnum status;
    private NotificationClassificationEnum classification;
    private Instant dueDate;

    private MaintenanceEntity maintenance;
    private TaskEntity task;

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

    @OneToMany
    public Set<ReplyEntity> getCommunication() {
        return communication;
    }

    public NotificationEntity setCommunication(Set<ReplyEntity> communication) {
        this.communication = communication;
        return this;
    }

    @ManyToOne
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
    @Column(name = "classification")
    public NotificationClassificationEnum getClassification() {
        return classification;
    }

    public NotificationEntity setClassification(NotificationClassificationEnum classification) {
        this.classification = classification;
        return this;
    }

    @Column(name = "due_date", nullable = false)
    public Instant getDueDate() {
        return dueDate;
    }

    public NotificationEntity setDueDate(Instant dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public MaintenanceEntity getMaintenance() {
        return maintenance;
    }

    public NotificationEntity setMaintenance(MaintenanceEntity maintenance) {
        this.maintenance = maintenance;
        return this;
    }

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public TaskEntity getTask() {
        return task;
    }

    public NotificationEntity setTask(TaskEntity task) {
        this.task = task;
        return this;
    }

    //    @ManyToMany(cascade = CascadeType.MERGE)
//    @JoinTable(
//            name = "issues_tasks",
//            joinColumns = @JoinColumn(name = "issue_id"),
//            inverseJoinColumns = @JoinColumn(name = "task_id"))
//    public Set<TaskEntity> getTasks() {
//        return tasks;
//    }
//
//    public NotificationEntity setTasks(Set<TaskEntity> tasks) {
//        this.tasks = tasks;
//        return this;
//    }
}
