package managing.tool.e_aircraft.web;

import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static managing.tool.constants.GlobalConstants.*;

@RestController
@CrossOrigin(FRONTEND_URL)
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AircraftViewDto>> getALl(){
        return ResponseEntity
                    .ok()
                    .body(this.aircraftService.findAll());
    }

    @PutMapping("/{registration}/update")
    public ResponseEntity<AircraftViewDto> updateSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String registration, @RequestBody AircraftViewDto aircraftDataForUpdate ){

        if(!this.aircraftService.aircraftExists(registration)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, registration), "registration");
        }

        AircraftViewDto aircraftUpdated = this.aircraftService.updateAircraft(aircraftDataForUpdate, jwt);

        return new ResponseEntity<>(aircraftUpdated, HttpStatus.OK);
    }

    @PutMapping("/{registration}/create")
    public ResponseEntity<AircraftViewDto> createSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String registration, @RequestBody AircraftViewDto aircraftNew ){

        if(this.aircraftService.aircraftExists(registration)){
            throw new FoundInDb(String.format(FOUNDERROR, registration), "registration");
        }

        AircraftViewDto aircraftCreated = this.aircraftService.createAircraft(aircraftNew, jwt);

        return new ResponseEntity<>(aircraftCreated, HttpStatus.OK);
    }
}
