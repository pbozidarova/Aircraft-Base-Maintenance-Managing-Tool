package managing.tool.e_notification.web;

import managing.tool.e_maintenance.web.MaintenanceController;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_task.model.dto.TaskViewDto;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;


    @Autowired
    public NotificationController(NotificationService issueService) {
        this.notificationService = issueService;

    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationViewDto>> findAllIssues(){
        return ResponseEntity.ok()
                .body(this.notificationService.findAllIssues());
    }

    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllIssuesRaisedBy(@PathVariable String companyNum){

        List<EntityModel<NotificationViewDto>> issues = this.notificationService
                .findAllIssuesByAuthor(companyNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));
    }

    @GetMapping("/maintenance/{maintenanceNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllNotifForMaintenance(@PathVariable String maintenanceNum){
        List<EntityModel<NotificationViewDto>> issues = this.notificationService
                .findAllIssuesByMaintenance(maintenanceNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));
    }

    @GetMapping("/task/{taskNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllNotifForTask
            (@PathVariable String taskNum){

        List<EntityModel<NotificationViewDto>> notifications = this.notificationService
                .findAllNotifForTask(taskNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(notifications));
    }

//    private Link[] createNotifHypermedia(NotificationViewDto notificationViewDto) {
//        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(MaintenanceController.class)
//                .findMaintenanceByNum(maintenance.getMaintenanceNum())).withSelfRel();
//        result.add(selfLink);

//        Link tasksLink = linkTo(methodOn(MaintenanceController.class)
//                .findAllMaintenanceByTaskNum(notificationViewDto.getIssueNum()))
//                .withRel("maintenance");
//        result.add(tasksLink);
//
//        Link notificationsLink = linkTo(methodOn(NotificationController.class)
//                .findAllNotifForTask(notificationViewDto.getIssueNum()))
//                .withRel("notifications");
//        result.add(notificationsLink);
//
//        return result.toArray(new Link[0]);
   // }

}
