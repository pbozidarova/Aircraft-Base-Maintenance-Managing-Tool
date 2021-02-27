package managing.tool.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tasks")
public class Task extends BaseEntity {

    private String taskNum;
    private String code;
    private String description;

    private boolean isToolingAvailable;
    private boolean areJobCardsPrepared;
    private boolean isQualityAssured;

    private Set<Maintenance> maintenances;
    private Set<User> preparedBy;
    private Set<Ticket> tickets;
    private Set<Aircraft> applicableAircraft;

    public Task() {
    }

    public String getTaskNum() {
        return taskNum;
    }

    public Task setTaskNum(String taskNum) {
        this.taskNum = taskNum;
        return this;
    }

    public String getCode() {
        return code;
    }

    public Task setCode(String code) {
        this.code = code;
        return this;
    }
    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Task setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isToolingAvailable() {
        return isToolingAvailable;
    }

    public Task setToolingAvailable(boolean toolingAvailable) {
        isToolingAvailable = toolingAvailable;
        return this;
    }

    public boolean isAreJobCardsPrepared() {
        return areJobCardsPrepared;
    }

    public Task setAreJobCardsPrepared(boolean areJobCardsPrepared) {
        this.areJobCardsPrepared = areJobCardsPrepared;
        return this;
    }

    public boolean isQualityAssured() {
        return isQualityAssured;
    }

    public Task setQualityAssured(boolean qualityAssured) {
        isQualityAssured = qualityAssured;
        return this;
    }

    @ManyToMany(mappedBy = "tasks", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<Maintenance> getMaintenances() {
        return maintenances;
    }

    public Task setMaintenances(Set<Maintenance> maintenances) {
        this.maintenances = maintenances;
        return this;
    }

    @ManyToMany
    public Set<User> getPreparedBy() {
        return preparedBy;
    }

    public Task setPreparedBy(Set<User> preparedBy) {
        this.preparedBy = preparedBy;
        return this;
    }

    @ManyToMany(mappedBy = "tasks")
    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Task setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    @ManyToMany
    public Set<Aircraft> getApplicableAircraft() {
        return applicableAircraft;
    }

    public Task setApplicableAircraft(Set<Aircraft> applicableAircraft) {
        this.applicableAircraft = applicableAircraft;
        return this;
    }
}
