package managing.tool.e_issue.web;

import managing.tool.e_issue.model.dto.IssueViewDto;
import managing.tool.e_issue.service.IssueService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
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

    private final IssueService issueService;


    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;

    }

    @GetMapping("/all")
    public ResponseEntity<List<IssueViewDto>> findAllIssues(){
        return ResponseEntity.ok()
                .body(this.issueService.findAllIssues());
    }

    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<IssueViewDto>>> findAllIssuesRaisedBy(@PathVariable String companyNum){

        List<EntityModel<IssueViewDto>> issues = this.issueService
                .findAllIssuesByAuthor(companyNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));


    }

    @GetMapping("/maintenance/{maintenanceNum}")
    public ResponseEntity<CollectionModel<EntityModel<IssueViewDto>>> findAllIssuesForMaintenance(@PathVariable String maintenanceNum){
        List<EntityModel<IssueViewDto>> issues = this.issueService
                .findAllIssuesByMaintenance(maintenanceNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(issues));
    }

}
