package managing.tool.e_aircraft.web;

import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.web.FacilityController;
import managing.tool.e_maintenance.web.MaintenanceController;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_user.web.UserController;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<AircraftViewDto>>> getAll(){
        List<EntityModel<AircraftViewDto>> aircraft = this.aircraftService
                .findAll()
                .stream()
                .map(a -> EntityModel.of(a, createAircraftHypermedia(a)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        aircraft,
                        linkTo(methodOn(AircraftController.class).getAll()).withSelfRel())
        );
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

    private Link[] createAircraftHypermedia(AircraftViewDto aircraftViewDto) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(UserController.class)
//                            .findSingleUser(user.getCompanyNum())).withSelfRel();
//        result.add(selfLink);

        Link maintenanceLink = linkTo(methodOn(MaintenanceController.class)
                .maintenanceByAircraft(aircraftViewDto.getAircraftRegistration()))
                .withRel("maintenance");
        result.add(maintenanceLink);


        return result.toArray(new Link[0]);
    }
}
