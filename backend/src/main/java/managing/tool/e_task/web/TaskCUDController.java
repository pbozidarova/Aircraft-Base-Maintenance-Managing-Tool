package managing.tool.e_task.web;

import managing.tool.aop.TrackCreation;
import managing.tool.aop.TrackUpdating;
import managing.tool.e_task.model.dto.TaskRequestDto;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotAuthorized;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import static managing.tool.constants.GlobalConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(FRONTEND_URL)
public class TaskCUDController {
    private final TaskService taskService;

    public TaskCUDController(TaskService taskService) {
        this.taskService = taskService;
    }

    @TrackUpdating(updatingMethod = "updateTask")
    @PutMapping("/{taskNum}/update")
    public ResponseEntity<TaskViewDto> updateTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum,
            @RequestBody TaskRequestDto taskDataForUpdate,
            @AuthenticationPrincipal User principal) {
        if(principal.getAuthorities().toString().contains("ROLE_ENGINEER")){
            this.taskService.evictCachedTasks();
            TaskViewDto taskUpdated = this.taskService.updateTask(taskNum, taskDataForUpdate, jwt);

            return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
        }else {
            throw new NotAuthorized(String.format(NOT_AUTHORIZED_ERROR, "an ENGINEER"), "authority");
        }
    }

    @TrackCreation(creatingMethod = "createTask")
    @PutMapping("/{taskNum}/create")
    public ResponseEntity<TaskViewDto> createTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum,
            @RequestBody TaskRequestDto taskNew,
            @AuthenticationPrincipal User principal) {
        if(principal.getAuthorities().toString().contains("ROLE_ENGINEER")){
            this.taskService.evictCachedTasks();
            TaskViewDto task = this.taskService.createTask(taskNum, taskNew, jwt);

            return new ResponseEntity<>(task, HttpStatus.OK);
        }else {
            throw new NotAuthorized(String.format(NOT_AUTHORIZED_ERROR, "an ENGINEER"), "authority");
        }
    }


}
