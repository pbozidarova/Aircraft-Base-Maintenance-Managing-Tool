package managing.tool.e_aircraft.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.service.AircraftValidationService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.stereotype.Service;

import static managing.tool.constants.GlobalConstants.NOTFOUND_SELECT_ERROR;

@Service
@AllArgsConstructor
public class AircraftValidationServiceImpl implements AircraftValidationService {
    private final AircraftService aircraftService;

    @Override
    public void validateIfAircraftExists(String registration) {

        if(!this.aircraftService.aircraftExists(registration)){
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, registration, "aircraft"),
                    "aircraft"
            );
        }
    }
}
