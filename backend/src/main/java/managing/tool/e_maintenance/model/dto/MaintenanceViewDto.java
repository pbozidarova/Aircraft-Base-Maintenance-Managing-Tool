package managing.tool.e_maintenance.model.dto;

import java.time.LocalDate;

public class MaintenanceViewDto {
    private String maintenanceNum;
    private String aircraftRegistration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String facility;
    private String responsibleEngineer;

    public MaintenanceViewDto() {
    }

    public String getMaintenanceNum() {
        return maintenanceNum;
    }

    public MaintenanceViewDto setMaintenanceNum(String maintenanceNum) {
        this.maintenanceNum = maintenanceNum;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public MaintenanceViewDto setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MaintenanceViewDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public MaintenanceViewDto setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MaintenanceViewDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public MaintenanceViewDto setFacility(String facility) {
        this.facility = facility;
        return this;
    }


    public String getResponsibleEngineer() {
        return responsibleEngineer;
    }

    public MaintenanceViewDto setResponsibleEngineer(String responsibleEngineer) {
        this.responsibleEngineer = responsibleEngineer;
        return this;
    }
}
