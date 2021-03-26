package managing.tool.e_facility.web;

import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_maintenance.web.MaintenanceController;
import managing.tool.e_user.web.UserController;
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

import static managing.tool.constants.GlobalConstants.FOUNDERROR;
import static managing.tool.constants.GlobalConstants.NOTFOUNDERROR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(GlobalConstants.FRONTEND_URL)
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<FacilityViewDto>>> allFacility(){
        List<EntityModel<FacilityViewDto>> facilities = this.facilityService
                .findAll()
                .stream()
                .map(f -> EntityModel.of(f, createFacilityHypermedia(f)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        facilities,
                        linkTo(methodOn(FacilityController.class).allFacility()).withSelfRel())
        );
    }

    @PutMapping("/{name}/update")
    public ResponseEntity<FacilityViewDto> updateSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String name, @RequestBody FacilityViewDto facilityDataForUpdate ){

        if(!this.facilityService.facilityExists(name)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, name), "name");
        }

        FacilityViewDto facilityUpdated = this.facilityService.updateFacility(facilityDataForUpdate, jwt);

        return new ResponseEntity<>(facilityUpdated, HttpStatus.OK);
    }

    @PutMapping("/{name}/create")
    public ResponseEntity<FacilityViewDto> createSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String name, @RequestBody FacilityViewDto facilityNew ){

        if(this.facilityService.facilityExists(name)){
            throw new FoundInDb(String.format(FOUNDERROR, name), "name");
        }

        FacilityViewDto facilityCreated = this.facilityService.createFacility(facilityNew, jwt);

        return new ResponseEntity<>(facilityCreated, HttpStatus.OK);
    }

    private Link[] createFacilityHypermedia(FacilityViewDto facility) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(UserController.class)
//                            .findSingleUser(user.getCompanyNum())).withSelfRel();
//        result.add(selfLink);

        Link usersLink = linkTo(methodOn(UserController.class)
                .usersFromFacility(facility.getName()))
                .withRel("users");
        result.add(usersLink);

        Link maintenanceLink = linkTo(methodOn(MaintenanceController.class)
                .maintenanceInFacility(facility.getName()))
                .withRel("maintenance");
        result.add(maintenanceLink);


        return result.toArray(new Link[0]);
    }

}
