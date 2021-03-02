package managing.tool.e_aircraft.service;

import managing.tool.e_aircraft.model.AircraftEntity;

import java.io.FileNotFoundException;

public interface AircraftService {
    void seedAircraft() throws FileNotFoundException;
    boolean aircraftAreImported();

    AircraftEntity getAircraftByRegistration(String registration);
}
