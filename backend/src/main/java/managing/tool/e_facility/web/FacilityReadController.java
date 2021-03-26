package managing.tool.e_facility.web;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_maintenance.web.MaintenanceReadController;
import managing.tool.e_user.web.UserReadController;
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
@AllArgsConstructor
public class FacilityReadController {

    private final FacilityService facilityService;

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
                        linkTo(methodOn(FacilityReadController.class).allFacility()).withSelfRel())
        );
    }


    private Link[] createFacilityHypermedia(FacilityViewDto facility) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(UserController.class)
//                            .findSingleUser(user.getCompanyNum())).withSelfRel();
//        result.add(selfLink);

        Link usersLink = linkTo(methodOn(UserReadController.class)
                .usersFromFacility(facility.getName()))
                .withRel("users");
        result.add(usersLink);

        Link maintenanceLink = linkTo(methodOn(MaintenanceReadController.class)
                .maintenanceInFacility(facility.getName()))
                .withRel("maintenance");
        result.add(maintenanceLink);


        return result.toArray(new Link[0]);
    }

}
