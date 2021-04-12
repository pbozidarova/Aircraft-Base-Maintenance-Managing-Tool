package managing.tool.e_maintenance.web;

import lombok.AllArgsConstructor;
import managing.tool.aop.TrackCreation;
import managing.tool.aop.TrackUpdating;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_maintenance.event.MaintenanceEventPublisher;
import managing.tool.e_maintenance.model.dto.MaintenanceRequestDto;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

import static managing.tool.constants.GlobalConstants.FOUNDERROR;
import static managing.tool.constants.GlobalConstants.NOTFOUNDERROR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(GlobalConstants.FRONTEND_URL)
@RequestMapping("/maintenance")
@AllArgsConstructor
public class MaintenanceCUDController {

    private final MaintenanceService maintenanceService;
    private final MaintenanceEventPublisher publisher;

    @TrackUpdating(updatingMethod = "updateMaintenance")
    @PutMapping("/{maintenanceNum}/update")
    public ResponseEntity<MaintenanceViewDto> updateMaintenance(
            @RequestHeader("authorization") String jwt,
            @PathVariable String maintenanceNum, @RequestBody MaintenanceRequestDto maintenanceDataForUpdate ) throws NotFoundInDb {

        this.maintenanceService.evictCachedMaintenance();
        MaintenanceViewDto maintenanceUpdated = this.maintenanceService.updateMaintenance(maintenanceNum, maintenanceDataForUpdate, jwt);

        return new ResponseEntity<>(maintenanceUpdated, HttpStatus.OK);
    }

    @TrackCreation(creatingMethod = "createMaintenance")
    @Transactional
    @PutMapping("/{maintenanceNum}/create")
    public ResponseEntity<MaintenanceViewDto> createMaintenance(
            @RequestHeader("authorization") String jwt,
            @PathVariable String maintenanceNum, @RequestBody MaintenanceRequestDto maintenanceNew ) throws FoundInDb {

        this.maintenanceService.evictCachedMaintenance();
        publisher.publishEvent(maintenanceNum);

        MaintenanceViewDto maintenanceCreated = this.maintenanceService.createMaintenance(maintenanceNum, maintenanceNew, jwt);

        return new ResponseEntity<>(maintenanceCreated, HttpStatus.OK);
    }


}
