package managing.tool.e_notification.web;

import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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


}
