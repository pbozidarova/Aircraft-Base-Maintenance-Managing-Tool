package managing.tool.service;

import managing.tool.model.entity.Aircraft;

import java.io.FileNotFoundException;

public interface AircraftService {
    void seedAircraft() throws FileNotFoundException;
    boolean aircraftAreImported();

    Aircraft getAircraftByRegistration(String registration);
}
