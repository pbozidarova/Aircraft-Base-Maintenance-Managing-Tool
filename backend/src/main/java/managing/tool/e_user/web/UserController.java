package managing.tool.e_user.web;

import managing.tool.e_notification.web.IssueController;
import managing.tool.e_maintenance.web.MaintenanceController;
import managing.tool.e_task.web.TaskController;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<CollectionModel<EntityModel<UserViewDto>>> allUsers(){
        List<EntityModel<UserViewDto>> users = this.userService
                .findAllUsers()
                .stream()
                .map(u -> EntityModel.of(u, createUserHypermedia(u)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                                CollectionModel.of(
                                        users,
                                        linkTo(methodOn(UserController.class).allUsers()).withSelfRel())
        );

    }

    @GetMapping("/{companyNum}")
    public ResponseEntity<EntityModel<UserViewDto>> findSingleUser(@PathVariable String companyNum){
        UserViewDto user = this.userService.findUser(companyNum);

        return ResponseEntity
                .ok(EntityModel.of(user, createUserHypermedia(user)));
    }

    private Link[] createUserHypermedia(UserViewDto user) {
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
