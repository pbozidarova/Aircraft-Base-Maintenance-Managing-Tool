package managing.tool.e_facility.web;

import lombok.AllArgsConstructor;
import managing.tool.aop.TrackCreation;
import managing.tool.aop.TrackUpdating;
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
public class FacilityCUDController {

    private final FacilityService facilityService;

    @TrackUpdating(updatingMethod = "updateFacility")
    @PutMapping("/{name}/update")
    public ResponseEntity<FacilityViewDto> updateFacility(
            @RequestHeader("authorization") String jwt,
            @PathVariable String name, @RequestBody FacilityViewDto facilityDataForUpdate )  {

        FacilityViewDto facilityUpdated = this.facilityService.updateFacility(name, facilityDataForUpdate, jwt);

        return new ResponseEntity<>(facilityUpdated, HttpStatus.OK);
    }

    @TrackCreation(creatingMethod = "createFacility")
    @PutMapping("/{name}/create")
    public ResponseEntity<FacilityViewDto> createFacility(
            @RequestHeader("authorization") String jwt,
            @PathVariable String name, @RequestBody FacilityViewDto facilityNew )  {

        FacilityViewDto facilityCreated = this.facilityService.createFacility(name, facilityNew, jwt);

        return new ResponseEntity<>(facilityCreated, HttpStatus.OK);
    }



}
