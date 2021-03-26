package managing.tool.e_maintenance.web;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.web.NotificationController;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_task.web.TaskReadController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(GlobalConstants.FRONTEND_URL)
@RequestMapping("/maintenance")
@AllArgsConstructor
public class MaintenanceReadController {

    private final MaintenanceService maintenanceService;
    private final TaskService taskService;


    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<MaintenanceViewDto>>> getAllMaintenanceEvents(){
        List<EntityModel<MaintenanceViewDto>> allMaintenanceEvents = this.maintenanceService
                .findAllMaintenanceEvents()
                .stream()
                .map(m -> EntityModel.of(m, createMaintenanceHypermedia(m)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        allMaintenanceEvents,
                        linkTo(methodOn(MaintenanceReadController.class).getAllMaintenanceEvents()).withSelfRel())
        );
    }

    @GetMapping("/{maintenanceNum}")
    public ResponseEntity<EntityModel<MaintenanceViewDto>> findMaintenanceByNum(@PathVariable String maintenanceNum){
        MaintenanceViewDto maintenance = this.maintenanceService.findMaintenanceByNum(maintenanceNum);

        return ResponseEntity
                .ok(EntityModel.of(maintenance, createMaintenanceHypermedia(maintenance)));
    }

    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<MaintenanceViewDto>>> findMaintenanceByResponsibleEngineer(@PathVariable String companyNum){
        List<EntityModel<MaintenanceViewDto>> maintenance = this.maintenanceService
                .findAllMaintenanceByResponsibleEngineer(companyNum)
                .stream()
                .map(m -> EntityModel.of(m, createMaintenanceHypermedia(m)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(maintenance)
                );
    }

    @GetMapping("/facility/{name}")
    public ResponseEntity<CollectionModel<EntityModel<MaintenanceViewDto>>> maintenanceInFacility(@PathVariable String name){

        List<EntityModel<MaintenanceViewDto>> maintenanceInFacilityByName = this.maintenanceService.
                findAllByFacility(name)
                .stream()
                .map(m -> EntityModel.of(m, createMaintenanceHypermedia(m)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(maintenanceInFacilityByName)
        );
    }

    @GetMapping("/aircraft/{registration}")
    public ResponseEntity<CollectionModel<EntityModel<MaintenanceViewDto>>> maintenanceByAircraft(@PathVariable String registration){

        List<EntityModel<MaintenanceViewDto>> maintenanceByAircraftRegistration = this.maintenanceService.
                findAllByAircraft(registration)
                .stream()
                .map(m -> EntityModel.of(m, createMaintenanceHypermedia(m)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(maintenanceByAircraftRegistration)
        );
    }


    @GetMapping("/task/{taskNum}")
    public ResponseEntity<CollectionModel<EntityModel<MaintenanceViewDto>>> findAllMaintenanceByTaskNum(@PathVariable String taskNum){

        List<EntityModel<MaintenanceViewDto>> allMaintenanceByTaskNum = this.taskService
                .findAllMaintenanceByTask(taskNum)
                .stream()
                .map(m -> EntityModel.of(m, createMaintenanceHypermedia(m)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(allMaintenanceByTaskNum));
    }


    private Link[] createMaintenanceHypermedia(MaintenanceViewDto maintenance) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(MaintenanceController.class)
//                .findMaintenanceByNum(maintenance.getMaintenanceNum())).withSelfRel();
//        result.add(selfLink);

        Link tasksLink = linkTo(methodOn(TaskReadController.class)
                .tasksInMaintenance(maintenance.getMaintenanceNum()))
                .withRel("tasks");
        result.add(tasksLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class)
                .findAllNotifForMaintenance(maintenance.getMaintenanceNum()))
                .withRel("notifications");
        result.add(notificationsLink);

        return result.toArray(new Link[0]);
    }
}
