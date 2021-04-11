package managing.tool.e_maintenance.model.dto;

import java.time.LocalDate;

public class MaintenanceRequestDto {
    private String maintenanceNum;
    private String aircraftRegistration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String facility;
    private String responsibleEngineer;

    public MaintenanceRequestDto() {
    }

    public String getMaintenanceNum() {
        return maintenanceNum;
    }

    public MaintenanceRequestDto setMaintenanceNum(String maintenanceNum) {
        this.maintenanceNum = maintenanceNum;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public MaintenanceRequestDto setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MaintenanceRequestDto setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public MaintenanceRequestDto setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public MaintenanceRequestDto setFacility(String facility) {
        this.facility = facility;
        return this;
    }


    public String getResponsibleEngineer() {
        return responsibleEngineer;
    }

    public MaintenanceRequestDto setResponsibleEngineer(String responsibleEngineer) {
        this.responsibleEngineer = responsibleEngineer;
        return this;
    }
}
