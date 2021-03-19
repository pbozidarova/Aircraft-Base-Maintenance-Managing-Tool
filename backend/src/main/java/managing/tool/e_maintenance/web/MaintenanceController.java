package managing.tool.e_maintenance.web;

import managing.tool.constants.GlobalConstants;
import managing.tool.e_maintenance.model.dto.MaintenanceViewModel;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.web.TaskController;
import managing.tool.e_user.model.dto.UserSingleViewDto;
import managing.tool.e_user.web.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(GlobalConstants.FRONTEND_URL)
@RequestMapping("/maintenance")
public class MaintenanceController {

    private final MaintenanceService maintenanceService;


    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<MaintenanceViewModel>> getAllMaintenanceEvents(){
        return ResponseEntity.ok()
                            .body(this.maintenanceService.findAllMaintenanceEvents());
    }

    @GetMapping("/{maintenanceNum}")
    public ResponseEntity<EntityModel<MaintenanceViewModel>> findSingleMaintenanceEvent(@PathVariable String maintenanceNum){
        MaintenanceViewModel maintenance = this.maintenanceService.findMaintenanceByNum(maintenanceNum);

        return ResponseEntity
                .ok(EntityModel.of(maintenance, createUserHypermedia(maintenance)));
    }

    private Link[] createUserHypermedia(MaintenanceViewModel maintenance) {
        List<Link> result = new ArrayList<>();

        Link selfLink = linkTo(methodOn(MaintenanceController.class)
                .findSingleMaintenanceEvent(maintenance.getMaintenanceNum())).withSelfRel();
        result.add(selfLink);

        Link tasksLink = linkTo(methodOn(TaskController.class)
                .tasksInMaintenance(maintenance.getMaintenanceNum()))
                .withRel("tasks");
        result.add(tasksLink);


        return result.toArray(new Link[0]);
    }
}
