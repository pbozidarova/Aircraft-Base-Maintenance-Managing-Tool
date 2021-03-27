package managing.tool.e_notification.web;

import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.service.NotificationService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@CrossOrigin
@RequestMapping("/notifications")
@AllArgsConstructor
public class NotificationREADController {

    private final NotificationService notificationService;

    @GetMapping("/all")
    public ResponseEntity<List<NotificationViewDto>> findAllIssues(){
        return ResponseEntity.ok()
                .body(this.notificationService.findAllNotifications());
    }

    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllIssuesRaisedBy(@PathVariable String companyNum){

        List<EntityModel<NotificationViewDto>> issues = this.notificationService
                .findAllNotificationsByAuthor(companyNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));
    }

    @GetMapping("/maintenance/{maintenanceNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllNotifForMaintenance(@PathVariable String maintenanceNum){
        List<EntityModel<NotificationViewDto>> issues = this.notificationService
                .findAllNotificationByMaintenance(maintenanceNum)
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
