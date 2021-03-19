package managing.tool.e_user.web;

import managing.tool.e_issue.web.IssueController;
import managing.tool.e_maintenance.web.MaintenanceController;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.web.TaskController;
import managing.tool.e_user.model.dto.UserAllViewDto;
import managing.tool.e_user.model.dto.UserSingleViewDto;
import managing.tool.e_user.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static managing.tool.constants.GlobalConstants.FRONTEND_URL;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@CrossOrigin(FRONTEND_URL)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<List<UserAllViewDto>> allUsers(){

        return  ResponseEntity
                    .ok()
                    .body(this.userService.findAllUsers());

    }

    @GetMapping("/{companyNum}")
    public ResponseEntity<EntityModel<UserSingleViewDto>> findSingleUser(@PathVariable String companyNum){
        UserSingleViewDto user = this.userService.findUser(companyNum);

        return ResponseEntity
                .ok(EntityModel.of(user, createUserHypermedia(user)));
    }

    private Link[] createUserHypermedia(UserSingleViewDto user) {
        List<Link> result = new ArrayList<>();

        Link selfLink = linkTo(methodOn(UserController.class)
                            .findSingleUser(user.getCompanyNum())).withSelfRel();
        result.add(selfLink);

        Link tasksLink = linkTo(methodOn(TaskController.class)
                            .tasksPreparedBy(user.getCompanyNum()))
                            .withRel("tasks");
        result.add(tasksLink);

        Link maintenanceLink = linkTo(methodOn(MaintenanceController.class)
                .findMaintenanceByResponsibleEngineer(user.getCompanyNum()))
                .withRel("maintenance");
        result.add(maintenanceLink);

        Link issuesLink = linkTo(methodOn(IssueController.class)
                .findAllIssuesRaisedBy(user.getCompanyNum()))
                .withRel("issues");
        result.add(issuesLink);


        return result.toArray(new Link[0]);
    }
}
