package managing.tool.e_aircraft.model.dto;

public class AircraftViewDto {

    private String aircraftRegistration ;
    private String serialNumber ;
    private String aircraftType ;
    private String aircraftModel ;
    private String operatorName;
    private String operatorICAOCode ;
    private String engineManufacturer ;
    private String engineModelSeries ;

    public AircraftViewDto() {
    }

    public String getOperatorName() {
        return operatorName;
    }

    public AircraftViewDto setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }

    public String getOperatorICAOCode() {
        return operatorICAOCode;
    }

    public AircraftViewDto setOperatorICAOCode(String operatorICAOCode) {
        this.operatorICAOCode = operatorICAOCode;
        return this;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public AircraftViewDto setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
        return this;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public AircraftViewDto setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public AircraftViewDto setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public AircraftViewDto setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public AircraftViewDto setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
        return this;
    }

    public String getEngineModelSeries() {
        return engineModelSeries;
    }

    public AircraftViewDto setEngineModelSeries(String engineModelSeries) {
        this.engineModelSeries = engineModelSeries;
        return this;
    }
}
