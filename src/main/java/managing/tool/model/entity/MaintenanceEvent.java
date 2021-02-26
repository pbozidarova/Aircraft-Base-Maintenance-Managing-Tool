package managing.tool.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "maintenance_events")
public class MaintenanceEvent extends BaseEntity {

   private long eventNum;
   private LocalDate startDate;
   private LocalDate endDate;
   private String status;
   private Facility facility;
   private Set<Aircraft> aircraft;
   private User engineer;

    public MaintenanceEvent() {
    }

    public long getEventNum() {
        return eventNum;
    }

    public MaintenanceEvent setEventNum(long eventNum) {
        this.eventNum = eventNum;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MaintenanceEvent setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public MaintenanceEvent setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MaintenanceEvent setStatus(String status) {
        this.status = status;
        return this;
    }

    public Facility getFacility() {
        return facility;
    }

    public MaintenanceEvent setFacility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public Set<Aircraft> getAircraft() {
        return aircraft;
    }

    public MaintenanceEvent setAircraft(Set<Aircraft> aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    public User getEngineer() {
        return engineer;
    }

    public MaintenanceEvent setEngineer(User engineer) {
        this.engineer = engineer;
        return this;
    }
}
