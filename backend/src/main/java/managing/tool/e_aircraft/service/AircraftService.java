package managing.tool.e_aircraft.service;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface AircraftService {

    List<AircraftViewDto> findAll();

    void seedAircraft() throws FileNotFoundException;
    boolean aircraftAreImported();

    AircraftEntity getAircraftByRegistration(String registration);
}
