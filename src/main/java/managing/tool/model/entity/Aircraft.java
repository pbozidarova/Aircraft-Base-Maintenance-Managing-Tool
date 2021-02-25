package managing.tool.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Aircraft extends BaseEntity {

    private String operatorName;
    private String operatorICAOCode ;
    private String aircraftType ;
    private String aircraftModel ;
    private String aircraftRegistration ;
    private String serialNumber ;
    private String engineManufacturer ;
    private String engineModelSeries ;

    public Aircraft() {
    }

    public String getOperatorName() {
        return operatorName;
    }

    public Aircraft setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }

    public String getOperatorICAOCode() {
        return operatorICAOCode;
    }

    public Aircraft setOperatorICAOCode(String operatorICAOCode) {
        this.operatorICAOCode = operatorICAOCode;
        return this;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public Aircraft setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
        return this;
    }

    public String getAircraftModel() {
        return aircraftModel;
    }

    public Aircraft setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public Aircraft setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Aircraft setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public Aircraft setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
        return this;
    }

    public String getEngineModelSeries() {
        return engineModelSeries;
    }

    public Aircraft setEngineModelSeries(String engineModelSeries) {
        this.engineModelSeries = engineModelSeries;
        return this;
    }


}
