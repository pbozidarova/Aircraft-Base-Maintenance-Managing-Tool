package managing.tool.e_aircraft.service;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface AircraftService {

    AircraftViewDto updateAircraft(AircraftViewDto aircraftDataForUpdate, String jwt);
    AircraftViewDto createAircraft(AircraftViewDto aircraftNew, String jwt);

    List<AircraftViewDto> findAll();
    AircraftEntity getAircraftByRegistration(String registration);

    Boolean aircraftExists(String registration);

}
