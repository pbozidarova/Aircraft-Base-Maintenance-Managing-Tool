package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.MaintenanceStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "maintenance")
public class Maintenance extends BaseEntity {

   private String maintenanceNum;
   private LocalDate startDate;
   private LocalDate endDate;
   private MaintenanceStatusEnum status;
   private Facility facility;
   private Aircraft aircraft;
   private User engineer;
   private Set<Task> tasks;

    public Maintenance() {
    }

    @Column(name = "maintenance_num", nullable = false)
    public String getMaintenanceNum() {
        return maintenanceNum;
    }

    public Maintenance setMaintenanceNum(String maintenance) {
        this.maintenanceNum = maintenance;
        return this;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public Maintenance setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
    @Column(name ="end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public Maintenance setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public MaintenanceStatusEnum getStatus() {
        return status;
    }

    public Maintenance setStatus(MaintenanceStatusEnum status) {
        this.status = status;
        return this;
    }

    @ManyToOne
    public Facility getFacility() {
        return facility;
    }

    public Maintenance setFacility(Facility facility) {
        this.facility = facility;
        return this;
    }

    @ManyToOne
    public Aircraft getAircraft() {
        return aircraft;
    }

    public Maintenance setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    @ManyToOne
    public User getEngineer() {
        return engineer;
    }

    public Maintenance setEngineer(User engineer) {
        this.engineer = engineer;
        return this;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<Task> getTasks() {
        return tasks;
    }

    public Maintenance setTasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }
}
