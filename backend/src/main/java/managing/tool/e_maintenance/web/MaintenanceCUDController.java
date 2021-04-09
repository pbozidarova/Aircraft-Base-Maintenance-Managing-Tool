package managing.tool.e_maintenance.web;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_maintenance.event.MaintenanceEventPublisher;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.service.TaskService;
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

    @PutMapping("/{maintenanceNum}/update")
    public ResponseEntity<MaintenanceViewDto> updateSingleMaintenance(
            @RequestHeader("authorization") String jwt,
            @PathVariable String maintenanceNum, @RequestBody MaintenanceViewDto maintenanceDataForUpdate ) throws NotFoundInDb {

        if(!this.maintenanceService.maintenanceExists(maintenanceNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, maintenanceNum), "taskNum");
        }

        this.maintenanceService.evictCachedMaintenance();
        MaintenanceViewDto maintenanceUpdated = this.maintenanceService.updateMaintenance(maintenanceDataForUpdate, jwt);

        return new ResponseEntity<>(maintenanceUpdated, HttpStatus.OK);
    }

    @Transactional
    @PutMapping("/{maintenanceNum}/create")
    public ResponseEntity<MaintenanceViewDto> createSingleMaintenance(
            @RequestHeader("authorization") String jwt,
            @PathVariable String maintenanceNum, @RequestBody MaintenanceViewDto maintenanceNew ) throws FoundInDb {

        if(this.maintenanceService.maintenanceExists(maintenanceNum)){
            throw new FoundInDb(String.format(FOUNDERROR, maintenanceNum), "maintenanceNum");
        }
        this.maintenanceService.evictCachedMaintenance();

        publisher.publishEvent(maintenanceNum);
        MaintenanceViewDto maintenanceCreated = this.maintenanceService.createMaintenance(maintenanceNew, jwt);

        return new ResponseEntity<>(maintenanceCreated, HttpStatus.OK);
    }


}
