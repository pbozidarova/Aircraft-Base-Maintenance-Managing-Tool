package managing.tool.e_task.web;

import managing.tool.aop.TrackCreation;
import managing.tool.aop.TrackUpdating;
import managing.tool.e_task.model.dto.TaskCreateDto;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
//            @AuthenticationPrincipal UserDetails principal,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskDataForUpdate ) throws NotFoundInDb {

//        System.out.println(principal.getUsername());

        if(!this.taskService.taskExists(taskNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, taskNum), "taskNum");
        }

        this.taskService.evictCachedTasks();
        TaskViewDto taskUpdated = this.taskService.updateTask(taskDataForUpdate, jwt);

        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @TrackCreation(creatingMethod = "createTask")
    @PutMapping("/{taskNum}/create")
    public ResponseEntity<TaskViewDto> createTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskCreateDto taskNew ) throws FoundInDb {

        if(this.taskService.taskExists(taskNum)){
            throw new FoundInDb(String.format(FOUNDERROR, taskNum), "taskNum");
        }

        this.taskService.evictCachedTasks();
        TaskViewDto task = this.taskService.createTask(taskNew, jwt);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }


}
