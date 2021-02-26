package managing.tool.model.dto.seed;

import com.google.gson.annotations.Expose;

public class MaintenanceEventSeedDto {
    @Expose
    private String eventNum;
    @Expose
    private String startDate;
    @Expose
    private String status;
    @Expose
    private String facility;
    @Expose
    private String aircraftRegistration;
    @Expose
    private String engineer;

    public MaintenanceEventSeedDto() {
    }

    public String getEventNum() {
        return eventNum;
    }

    public MaintenanceEventSeedDto setEventNum(String eventNum) {
        this.eventNum = eventNum;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public MaintenanceEventSeedDto setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MaintenanceEventSeedDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public MaintenanceEventSeedDto setFacility(String facility) {
        this.facility = facility;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public MaintenanceEventSeedDto setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public String getEngineer() {
        return engineer;
    }

    public MaintenanceEventSeedDto setEngineer(String engineer) {
        this.engineer = engineer;
        return this;
    }
}
