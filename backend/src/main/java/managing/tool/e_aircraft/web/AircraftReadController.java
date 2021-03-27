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
public class AircraftReadController {
    private final AircraftService aircraftService;

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
                        linkTo(methodOn(AircraftReadController.class).getAll()).withSelfRel())
        );
    }

    private Link[] createAircraftHypermedia(AircraftViewDto aircraftViewDto) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(UserController.class)
//                            .findSingleUser(user.getCompanyNum())).withSelfRel();
//        result.add(selfLink);

        Link maintenanceLink = linkTo(methodOn(MaintenanceReadController.class)
                .maintenanceByAircraft(aircraftViewDto.getAircraftRegistration()))
                .withRel("maintenance")
                .withTitle(String.format("Maintenance events that have been performed on %s.", aircraftViewDto.getAircraftRegistration()));
        result.add(maintenanceLink);


        return result.toArray(new Link[0]);
    }
}
