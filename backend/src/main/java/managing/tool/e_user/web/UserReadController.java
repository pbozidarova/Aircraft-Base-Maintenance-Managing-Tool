package managing.tool.e_user.web;

import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_maintenance.web.MaintenanceReadController;
import managing.tool.e_notification.web.NotificationController;
import managing.tool.e_task.web.TaskReadController;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserCreateUpdateService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
@CrossOrigin(FRONTEND_URL)
public class UserReadController {
    private final UserService userService;
    private final UserCreateUpdateService userCreateUpdateService;
    private final FacilityService facilityService;

    public UserReadController(UserService userService, UserCreateUpdateService userCreateUpdateService, FacilityService facilityService) {
        this.userService = userService;
        this.userCreateUpdateService = userCreateUpdateService;
        this.facilityService = facilityService;
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
                                        linkTo(methodOn(UserReadController.class).allUsers()).withSelfRel())
        );

    }

    @GetMapping("/{companyNum}")
    public ResponseEntity<EntityModel<UserViewDto>> findSingleUser(@PathVariable String companyNum){

        if(!this.userService.userExists(companyNum)){
            return ResponseEntity.notFound().build();
        }

        UserViewDto userViewDto = userService.findUser(companyNum);

        return ResponseEntity
                .ok(EntityModel.of(userViewDto, createUserHypermedia(userViewDto)));
    }

    @GetMapping("/facility/{name}")
    public ResponseEntity<CollectionModel<EntityModel<UserViewDto>>> usersFromFacility(@PathVariable String name){

        List<EntityModel<UserViewDto>> employeesFromFacility = this.facilityService.
                findAllUsersByFacilityName(name)
                .stream()
                .map(u -> EntityModel.of(u, createUserHypermedia(u)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(employeesFromFacility));
    }

    private Link[] createUserHypermedia(UserViewDto user) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(UserController.class)
//                            .findSingleUser(user.getCompanyNum())).withSelfRel();
//        result.add(selfLink);

        Link tasksLink = linkTo(methodOn(TaskReadController.class)
                            .tasksPreparedBy(user.getCompanyNum()))
                            .withRel("tasks")
                            .withTitle(String.format("Tasks that has been prepared by team in which %s - %s, %s has taken part!", user.getCompanyNum(), user.getFirstName(), user.getLastName()));

        result.add(tasksLink);

        Link maintenanceLink = linkTo(methodOn(MaintenanceReadController.class)
                .findMaintenanceByResponsibleEngineer(user.getCompanyNum()))
                .withRel("maintenance")
                .withTitle(String.format("Maintenance by responsible engineer %s - %s, %s!", user.getCompanyNum(), user.getFirstName(), user.getLastName()));
        result.add(maintenanceLink);

        Link issuesLink = linkTo(methodOn(NotificationController.class)
                .findAllIssuesRaisedBy(user.getCompanyNum()))
                .withRel("notifications")
                .withTitle(String.format("Notifications by author %s - %s, %s!", user.getCompanyNum(), user.getFirstName(), user.getLastName()));

        result.add(issuesLink);

        return result.toArray(new Link[0]);
    }
}
