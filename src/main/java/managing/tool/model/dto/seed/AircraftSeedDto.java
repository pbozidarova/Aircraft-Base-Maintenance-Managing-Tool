package managing.tool.model.dto.seed;

import com.google.gson.annotations.Expose;

public class AircraftSeedDto {
    @Expose
    private String operatorName;
    @Expose
    private String operatorICAOCode ;
    @Expose
    private String aircraftType ;
    @Expose
    private String aircraftModel ;
    @Expose
    private String aircraftRegistration ;
    @Expose
    private String serialNumber ;
    @Expose
    private String engineManufacturer ;
    @Expose
    private String engineModelSeries ;


    public AircraftSeedDto() {
    }

    public String getOperatorName() {
        return operatorName;
    }

    public AircraftSeedDto setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }

    public String getOperatorICAOCode() {
        return operatorICAOCode;
    }

    public AircraftSeedDto setOperatorICAOCode(String operatorICAOCode) {
        this.operatorICAOCode = operatorICAOCode;
        return this;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public AircraftSeedDto setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
        return this;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public AircraftSeedDto setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public AircraftSeedDto setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public AircraftSeedDto setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public AircraftSeedDto setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
        return this;
    }

    public String getEngineModelSeries() {
        return engineModelSeries;
    }

    public AircraftSeedDto setEngineModelSeries(String engineModelSeries) {
        this.engineModelSeries = engineModelSeries;
        return this;
    }
}
