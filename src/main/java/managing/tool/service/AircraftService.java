package managing.tool.service;

import managing.tool.model.entity.AircraftEntity;

import java.io.FileNotFoundException;

public interface AircraftService {
    void seedAircraft() throws FileNotFoundException;
    boolean aircraftAreImported();

    AircraftEntity getAircraftByRegistration(String registration);
}
