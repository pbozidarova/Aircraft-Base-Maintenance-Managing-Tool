package managing.tool.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class AircraftEntity extends BaseEntity {

    private String operatorName;
    private String operatorICAOCode ;
    private String aircraftType ;
    private String aircraftModel ;
    private String aircraftRegistration ;
    private String serialNumber ;
    private String engineManufacturer ;
    private String engineModelSeries ;

    public AircraftEntity() {
    }

    @Column(name = "operator_name", nullable = false)
    public String getOperatorName() {
        return operatorName;
    }

    public AircraftEntity setOperatorName(String operatorName) {
        this.operatorName = operatorName;
        return this;
    }
    @Column(name = "operator_ICAO_Code", nullable = false)
    public String getOperatorICAOCode() {
        return operatorICAOCode;
    }

    public AircraftEntity setOperatorICAOCode(String operatorICAOCode) {
        this.operatorICAOCode = operatorICAOCode;
        return this;
    }

    @Column(name ="aircraft_type", nullable = false)
    public String getAircraftType() {
        return aircraftType;
    }

    public AircraftEntity setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
        return this;
    }

    @Column(name = "aircraft_model", nullable = false)
    public String getAircraftModel() {
        return aircraftModel;
    }

    public AircraftEntity setAircraftModel(String aircraftModel) {
        this.aircraftModel = aircraftModel;
        return this;
    }

    @Column(name ="aircraft_registration", nullable = false)
    public String getAircraftRegistration() {
        return aircraftRegistration;
    }

    public AircraftEntity setAircraftRegistration(String aircraftRegistration) {
        this.aircraftRegistration = aircraftRegistration;
        return this;
    }

    @Column(name = "serial_number", nullable = false, unique = true)
    public String getSerialNumber() {
        return serialNumber;
    }

    public AircraftEntity setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    @Column(name = "engine_manufacturer", nullable = false)
    public String getEngineManufacturer() {
        return engineManufacturer;
    }

    public AircraftEntity setEngineManufacturer(String engineManufacturer) {
        this.engineManufacturer = engineManufacturer;
        return this;
    }
    @Column(name = "engine_model_series", nullable = false)
    public String getEngineModelSeries() {
        return engineModelSeries;
    }

    public AircraftEntity setEngineModelSeries(String engineModelSeries) {
        this.engineModelSeries = engineModelSeries;
        return this;
    }


}
