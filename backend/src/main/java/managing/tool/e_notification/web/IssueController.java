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
@RequestMapping("/issues")
public class IssueController {

    private final NotificationService issueService;


    @Autowired
    public IssueController(NotificationService issueService) {
        this.issueService = issueService;

    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationViewDto>> findAllIssues(){
        return ResponseEntity.ok()
                .body(this.issueService.findAllIssues());
    }

    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllIssuesRaisedBy(@PathVariable String companyNum){

        List<EntityModel<NotificationViewDto>> issues = this.issueService
                .findAllIssuesByAuthor(companyNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));


    }

    @GetMapping("/maintenance/{maintenanceNum}")
    public ResponseEntity<CollectionModel<EntityModel<NotificationViewDto>>> findAllIssuesForMaintenance(@PathVariable String maintenanceNum){
        List<EntityModel<NotificationViewDto>> issues = this.issueService
                .findAllIssuesByMaintenance(maintenanceNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));
    }

}
