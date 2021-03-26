package managing.tool.e_maintenance.web;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static managing.tool.constants.GlobalConstants.FOUNDERROR;
import static managing.tool.constants.GlobalConstants.NOTFOUNDERROR;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin(GlobalConstants.FRONTEND_URL)
@RequestMapping("/maintenance")
@AllArgsConstructor
public class MaintenanceCUDController {

    private final MaintenanceService maintenanceService;

    @PutMapping("/{maintenanceNum}/update")
    public ResponseEntity<MaintenanceViewDto> updateSingleMaintenance(
            @RequestHeader("authorization") String jwt,
            @PathVariable String maintenanceNum, @RequestBody MaintenanceViewDto maintenanceDataForUpdate ){

        if(!this.maintenanceService.maintenanceExists(maintenanceNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, maintenanceNum), "taskNum");
        }

        MaintenanceViewDto maintenanceUpdated = this.maintenanceService.updateMaintenance(maintenanceDataForUpdate, jwt);

        return new ResponseEntity<>(maintenanceUpdated, HttpStatus.OK);
    }

    @PutMapping("/{maintenanceNum}/create")
    public ResponseEntity<MaintenanceViewDto> createSingleMaintenance(
            @RequestHeader("authorization") String jwt,
            @PathVariable String maintenanceNum, @RequestBody MaintenanceViewDto maintenanceNew ){

        if(this.maintenanceService.maintenanceExists(maintenanceNum)){
            throw new FoundInDb(String.format(FOUNDERROR, maintenanceNum), "maintenanceNum");
        }

        MaintenanceViewDto maintenanceCreated = this.maintenanceService.createMaintenance(maintenanceNew, jwt);

        return new ResponseEntity<>(maintenanceCreated, HttpStatus.OK);
    }


}
