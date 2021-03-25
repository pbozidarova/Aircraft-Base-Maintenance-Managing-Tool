package managing.tool.e_maintenance.model.dto;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_maintenance.model.MaintenanceStatusEnum;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;

import java.time.LocalDate;
import java.util.Set;

public class MaintenanceViewModel {
    private String maintenanceNum;
    private String aircraftRegistration;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String facility;
    private String responsibleEngineer;

    public MaintenanceViewModel() {
    }

    public String getMaintenanceNum() {
        return maintenanceNum;
    }

    public MaintenanceViewModel setMaintenanceNum(String maintenanceNum) {
        this.maintenanceNum = maintenanceNum;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public MaintenanceViewModel setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public MaintenanceViewModel setStartDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public MaintenanceViewModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MaintenanceViewModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public MaintenanceViewModel setFacility(String facility) {
        this.facility = facility;
        return this;
    }


    public String getResponsibleEngineer() {
        return responsibleEngineer;
    }

    public MaintenanceViewModel setResponsibleEngineer(String responsibleEngineer) {
        this.responsibleEngineer = responsibleEngineer;
        return this;
    }
}
