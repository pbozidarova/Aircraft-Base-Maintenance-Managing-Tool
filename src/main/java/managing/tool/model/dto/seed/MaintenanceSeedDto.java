package managing.tool.model.dto.seed;

import com.google.gson.annotations.Expose;

public class MaintenanceSeedDto {
    @Expose
    private String maintenanceNum;
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

    public MaintenanceSeedDto() {
    }

    public String getEventNum() {
        return maintenanceNum;
    }

    public MaintenanceSeedDto setEventNum(String maintenanceNum) {
        this.maintenanceNum = maintenanceNum;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public MaintenanceSeedDto setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MaintenanceSeedDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public MaintenanceSeedDto setFacility(String facility) {
        this.facility = facility;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public MaintenanceSeedDto setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public String getEngineer() {
        return engineer;
    }

    public MaintenanceSeedDto setEngineer(String engineer) {
        this.engineer = engineer;
        return this;
    }
}
