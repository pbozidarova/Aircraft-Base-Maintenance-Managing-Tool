package managing.tool.e_task.web;

import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @PutMapping("/{taskNum}/update")
    public ResponseEntity<TaskViewDto> updateSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskDataForUpdate ) throws NotFoundInDb {

        if(!this.taskService.taskExists(taskNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, taskNum), "taskNum");
        }

        TaskViewDto taskUpdated = this.taskService.updateTask(taskDataForUpdate, jwt);

        return new ResponseEntity<>(taskUpdated, HttpStatus.OK);
    }

    @PutMapping("/{taskNum}/create")
    public ResponseEntity<TaskViewDto> createSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskNew ) throws FoundInDb {

        if(this.taskService.taskExists(taskNum)){
            throw new FoundInDb(String.format(FOUNDERROR, taskNum), "taskNum");
        }

        TaskViewDto task = this.taskService.createTask(taskNew, jwt);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }


}
