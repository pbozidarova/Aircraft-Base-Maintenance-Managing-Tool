package managing.tool.e_task.model;

import managing.tool.e_base.BaseEntity;
import managing.tool.e_issue.model.IssueEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_user.model.UserEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class TaskEntity extends BaseEntity {

    private String taskNum;
    private String code;
    private String description;

    private boolean isToolingAvailable;
    private boolean areJobCardsPrepared;
    private boolean isQualityAssured;

    private Set<MaintenanceEntity> maintenances;
    private Set<UserEntity> preparedBy;
    private Set<IssueEntity> tickets;

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
    @Column(name = "are_jobcards_preapred", nullable = false)
    public boolean areJobCardsPrepared() {
        return areJobCardsPrepared;
    }

    public TaskEntity setAreJobCardsPrepared(boolean areJobCardsPrepared) {
        this.areJobCardsPrepared = areJobCardsPrepared;
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

    @ManyToMany(mappedBy = "tasks")
    public Set<IssueEntity> getTickets() {
        return tickets;
    }

    public TaskEntity setTickets(Set<IssueEntity> tickets) {
        this.tickets = tickets;
        return this;
    }


}
