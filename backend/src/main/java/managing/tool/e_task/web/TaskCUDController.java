package managing.tool.e_task.web;

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
public class TaskCUDController {
    private final TaskService taskService;

    public TaskCUDController(TaskService taskService) {
        this.taskService = taskService;
    }


    @PutMapping("/{taskNum}/update")
    public ResponseEntity<TaskViewDto> updateSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskDataForUpdate ){

        if(!this.taskService.taskExists(taskNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, taskNum), "taskNum");
        }

        TaskViewDto taskUpdated = this.taskService.updateTask(taskDataForUpdate, jwt);

        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @PutMapping("/{taskNum}/create")
    public ResponseEntity<TaskViewDto> createSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskNew ){

        if(this.taskService.taskExists(taskNum)){
            throw new FoundInDb(String.format(FOUNDERROR, taskNum), "taskNum");
        }

        TaskViewDto task = this.taskService.createTask(taskNew, jwt);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }


}
