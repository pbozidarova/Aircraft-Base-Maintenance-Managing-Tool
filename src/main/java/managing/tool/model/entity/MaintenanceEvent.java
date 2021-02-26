package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.EventStatusEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Entity
@Table(name = "maintenance_events")
public class MaintenanceEvent extends BaseEntity {

   private String eventNum;
   private LocalDate startDate;
   private LocalDate endDate;
   private EventStatusEnum status;
   private Facility facility;
   private Aircraft aircraft;
   private User engineer;

    public MaintenanceEvent() {
    }

    public String getEventNum() {
        return eventNum;
    }

    public MaintenanceEvent setEventNum(String eventNum) {
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

    @Enumerated(EnumType.STRING)
    public EventStatusEnum getStatus() {
        return status;
    }

    public MaintenanceEvent setStatus(EventStatusEnum status) {
        this.status = status;
        return this;
    }

    @ManyToOne
    public Facility getFacility() {
        return facility;
    }

    public MaintenanceEvent setFacility(Facility facility) {
        this.facility = facility;
        return this;
    }

    @ManyToOne
    public Aircraft getAircraft() {
        return aircraft;
    }

    public MaintenanceEvent setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
        return this;
    }

    @ManyToOne
    public User getEngineer() {
        return engineer;
    }

    public MaintenanceEvent setEngineer(User engineer) {
        this.engineer = engineer;
        return this;
    }
}
