package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.MaintenanceStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "maintenance")
public class MaintenanceEntity extends BaseEntity {

   private String maintenanceNum;
   private LocalDate startDate;
   private LocalDate endDate;
   private MaintenanceStatusEnum status;
   private FacilityEntity facility;
   private AircraftEntity aircraft;
   private UserEntity engineer;
   private Set<TaskEntity> tasks;

    public MaintenanceEntity() {
    }

    @Column(name = "maintenance_num", nullable = false, unique = true)
    public String getMaintenanceNum() {
        return maintenanceNum;
    }

    public MaintenanceEntity setMaintenanceNum(String maintenance) {
        this.maintenanceNum = maintenance;
        return this;
    }

    @Column(name = "start_date", nullable = false)
    public LocalDate getStartDate() {
        return startDate;
    }

    public MaintenanceEntity setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }
    @Column(name ="end_date", nullable = false)
    public LocalDate getEndDate() {
        return endDate;
    }

    public MaintenanceEntity setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    public MaintenanceStatusEnum getStatus() {
        return status;
    }

    public MaintenanceEntity setStatus(MaintenanceStatusEnum status) {
        this.status = status;
        return this;
    }

    @ManyToOne
    public FacilityEntity getFacility() {
        return facility;
    }

    public MaintenanceEntity setFacility(FacilityEntity facility) {
        this.facility = facility;
        return this;
    }

    @ManyToOne
    public AircraftEntity getAircraft() {
        return aircraft;
    }

    public MaintenanceEntity setAircraft(AircraftEntity aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    @ManyToOne
    public UserEntity getEngineer() {
        return engineer;
    }

    public MaintenanceEntity setEngineer(UserEntity engineer) {
        this.engineer = engineer;
        return this;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<TaskEntity> getTasks() {
        return tasks;
    }

    public MaintenanceEntity setTasks(Set<TaskEntity> tasks) {
        this.tasks = tasks;
        return this;
    }
}
