package managing.tool.e_aircraft.web;

import lombok.AllArgsConstructor;
import managing.tool.aop.TrackCreation;
import managing.tool.aop.TrackUpdating;
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

    @TrackUpdating(updatingMethod = "updateAircraft")
    @PutMapping("/{registration}/update")
    public ResponseEntity<AircraftViewDto> updateAircraft(
            @RequestHeader("authorization") String jwt,
            @PathVariable String registration, @RequestBody AircraftViewDto aircraftDataForUpdate ) {

        AircraftViewDto aircraftUpdated = this.aircraftService.updateAircraft(registration, aircraftDataForUpdate, jwt);

        return new ResponseEntity<>(aircraftUpdated, HttpStatus.OK);
    }

    @TrackCreation(creatingMethod = "creteAircraft")
    @PutMapping("/{registration}/create")
    public ResponseEntity<AircraftViewDto> creteAircraft(
            @RequestHeader("authorization") String jwt,
            @PathVariable String registration, @RequestBody AircraftViewDto aircraftNew )  {

        AircraftViewDto aircraftCreated = this.aircraftService.createAircraft(registration, aircraftNew, jwt);

        return new ResponseEntity<>(aircraftCreated, HttpStatus.OK);
    }

}
