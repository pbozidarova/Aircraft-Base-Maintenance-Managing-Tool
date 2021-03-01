package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.IssueClassificationEnum;
import managing.tool.model.entity.enumeration.IssueStatusEnum;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "issues")
public class IssueEntity extends BaseEntity {

    private String issueNum;
    private Set<CommunicationEntity> communication;
    private UserEntity author;
    private IssueStatusEnum status;
    private IssueClassificationEnum classification;
    private LocalDateTime dateOfEntry;
    private LocalDateTime dueDate;

    private MaintenanceEntity maintenance;
    private Set<TaskEntity> tasks;

    public IssueEntity() {
    }

    @Column(name = "issue_num", nullable = false, unique = true)
    public String getIssueNum() {
        return issueNum;
    }

    public IssueEntity setIssueNum(String issueNum) {
        this.issueNum = issueNum;
        return this;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<CommunicationEntity> getCommunication() {
        return communication;
    }

    public IssueEntity setCommunication(Set<CommunicationEntity> replay) {
        this.communication = replay;
        return this;
    }

    @OneToOne
    public UserEntity getAuthor() {
        return author;
    }

    public IssueEntity setAuthor(UserEntity createdBy) {
        this.author = createdBy;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public IssueStatusEnum getStatus() {
        return status;
    }

    public IssueEntity setStatus(IssueStatusEnum status) {
        this.status = status;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = false)
    public IssueClassificationEnum getClassification() {
        return classification;
    }

    public IssueEntity setClassification(IssueClassificationEnum classification) {
        this.classification = classification;
        return this;
    }

    @Column(name = "date_of_entry", nullable = false)
    public LocalDateTime getDateOfEntry() {
        return dateOfEntry;
    }

    public IssueEntity setDateOfEntry(LocalDateTime dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
        return this;
    }

    @Column(name = "due_date", nullable = false)
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public IssueEntity setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @OneToOne
    public MaintenanceEntity getMaintenance() {
        return maintenance;
    }

    public IssueEntity setMaintenance(MaintenanceEntity maintenance) {
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

    public IssueEntity setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }
}
