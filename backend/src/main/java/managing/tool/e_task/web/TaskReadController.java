package managing.tool.e_task.web;

import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.web.MaintenanceReadController;
import managing.tool.e_notification.web.NotificationController;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
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
@RequestMapping("/tasks")
@CrossOrigin(FRONTEND_URL)
@AllArgsConstructor
public class TaskReadController {
    private final TaskService taskService;

    @GetMapping("all")
    public ResponseEntity<CollectionModel<EntityModel<TaskViewDto>>> allTasks(){
        List<EntityModel<TaskViewDto>> allTasks = this.taskService
                .findAllTasks()
                .stream()
                .map(t -> EntityModel.of(t, createTasksHypermedia(t)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(
                        allTasks,
                        linkTo(methodOn(TaskReadController.class).allTasks()).withSelfRel())
        );
    }

    @GetMapping("/{taskNum}")
    public ResponseEntity<TaskViewDto> findTask(@PathVariable String taskNum){
        return ResponseEntity.ok().body(this.taskService.findTask(taskNum));
    }

    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<TaskViewDto>>> tasksPreparedBy(@PathVariable String companyNum){

        List<EntityModel<TaskViewDto>> tasks = this.taskService
                .findAllByAuthor(companyNum)
                .stream()
                .map(t -> EntityModel.of(t, createTasksHypermedia(t)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(tasks));

    }

    @GetMapping("/maintenance/{maintenanceNum}")
    public ResponseEntity<CollectionModel<EntityModel<TaskViewDto>>> tasksInMaintenance(@PathVariable String maintenanceNum){

        List<EntityModel<TaskViewDto>> tasks = this.taskService
                .findAllAddedInMaintenance(maintenanceNum)
                .stream()
                .map(t -> EntityModel.of(t, createTasksHypermedia(t)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(tasks));

    }

    private Link[] createTasksHypermedia(TaskViewDto taskViewDto) {
        List<Link> result = new ArrayList<>();

//        Link selfLink = linkTo(methodOn(MaintenanceController.class)
//                .findMaintenanceByNum(maintenance.getMaintenanceNum())).withSelfRel();
//        result.add(selfLink);

        Link tasksLink = linkTo(methodOn(MaintenanceReadController.class)
                .findAllMaintenanceByTaskNum(taskViewDto.getTaskNum()))
                .withRel("maintenance");
        result.add(tasksLink);

        Link notificationsLink = linkTo(methodOn(NotificationController.class)
                .findAllNotifForTask(taskViewDto.getTaskNum()))
                .withRel("notifications");
        result.add(notificationsLink);

        return result.toArray(new Link[0]);
    }

}
