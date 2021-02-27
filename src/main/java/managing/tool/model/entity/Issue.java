package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.IssueClassification;
import managing.tool.model.entity.enumeration.IssueStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "issues")
public class Issue extends BaseEntity {

    private String issueNum;
    private Set<Communication> communication;
    private User author;
    private IssueStatus status;
    private IssueClassification classification;
    private LocalDateTime dateOfEntry;
    private LocalDateTime dueDate;

    private Maintenance maintenance;
    private Set<Task> tasks;

    public Issue() {
    }

    @Column(name = "issue_num", nullable = false)
    public String getIssueNum() {
        return issueNum;
    }

    public Issue setIssueNum(String issueNum) {
        this.issueNum = issueNum;
        return this;
    }

    @OneToMany(fetch = FetchType.EAGER)
    public Set<Communication> getCommunication() {
        return communication;
    }

    public Issue setCommunication(Set<Communication> replay) {
        this.communication = replay;
        return this;
    }

    @OneToOne
    public User getAuthor() {
        return author;
    }

    public Issue setAuthor(User createdBy) {
        this.author = createdBy;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public IssueStatus getStatus() {
        return status;
    }

    public Issue setStatus(IssueStatus status) {
        this.status = status;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "classification", nullable = false)
    public IssueClassification getClassification() {
        return classification;
    }

    public Issue setClassification(IssueClassification classification) {
        this.classification = classification;
        return this;
    }

    @Column(name = "date_of_entry", nullable = false)
    public LocalDateTime getDateOfEntry() {
        return dateOfEntry;
    }

    public Issue setDateOfEntry(LocalDateTime dateOfEntry) {
        this.dateOfEntry = dateOfEntry;
        return this;
    }

    @Column(name = "due_date", nullable = false)
    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public Issue setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    @OneToOne
    public Maintenance getMaintenance() {
        return maintenance;
    }

    public Issue setMaintenance(Maintenance maintenance) {
        this.maintenance = maintenance;
        return this;
    }

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "issues_tasks",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id"))
    public Set<Task> getTasks() {
        return tasks;
    }

    public Issue setTasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
