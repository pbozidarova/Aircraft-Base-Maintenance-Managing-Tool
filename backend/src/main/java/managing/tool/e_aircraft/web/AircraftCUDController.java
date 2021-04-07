package managing.tool.e_aircraft.web;

import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_maintenance.web.MaintenanceReadController;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(FRONTEND_URL)
@RequestMapping("/aircraft")
@AllArgsConstructor
public class AircraftCUDController {

    private final AircraftService aircraftService;

    @PutMapping("/{registration}/update")
    public ResponseEntity<AircraftViewDto> updateSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String registration, @RequestBody AircraftViewDto aircraftDataForUpdate ) throws NotFoundInDb {

        if(!this.aircraftService.aircraftExists(registration)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, registration), "registration");
        }

        AircraftViewDto aircraftUpdated = this.aircraftService.updateAircraft(aircraftDataForUpdate, jwt);

        return new ResponseEntity<>(aircraftUpdated, HttpStatus.OK);
    }

    @PutMapping("/{registration}/create")
    public ResponseEntity<AircraftViewDto> createSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String registration, @RequestBody AircraftViewDto aircraftNew ) throws FoundInDb {

        if(this.aircraftService.aircraftExists(registration)){
            throw new FoundInDb(String.format(FOUNDERROR, registration), "registration");
        }

        AircraftViewDto aircraftCreated = this.aircraftService.createAircraft(aircraftNew, jwt);

        return new ResponseEntity<>(aircraftCreated, HttpStatus.OK);
    }

}
