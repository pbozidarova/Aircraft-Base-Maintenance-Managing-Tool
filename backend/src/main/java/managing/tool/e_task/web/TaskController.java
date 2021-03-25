package managing.tool.e_task.web;

import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.*;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(FRONTEND_URL)
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("all")
    public ResponseEntity<List<TaskViewDto>> allTasks(){
        List<TaskViewDto> allTasks = this.taskService.findAllTasks();

        return ResponseEntity.ok().body(allTasks);
    }

    @GetMapping("/{taskNum}")
    public ResponseEntity<TaskViewDto> findTask(@PathVariable String taskNum){
        return ResponseEntity.ok().body(this.taskService.findTask(taskNum));
    }

    @PutMapping("/{taskNum}/update")
    public ResponseEntity<TaskViewDto> updateSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskViewDto ){

        if(!this.taskService.taskExists(taskNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, taskNum), "taskNum");
        }

        TaskViewDto task = this.taskService.updateTask(taskViewDto, jwt);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/{taskNum}/create")
    public ResponseEntity<TaskViewDto> createSingleTask(
            @RequestHeader("authorization") String jwt,
            @PathVariable String taskNum, @RequestBody TaskViewDto taskViewDto ){

        if(this.taskService.taskExists(taskNum)){
            throw new NotFoundInDb(String.format(FOUNDERROR, taskNum), "taskNum");
        }

        TaskViewDto task = this.taskService.createTask(taskViewDto, jwt);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @GetMapping("/user/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<TaskViewDto>>> tasksPreparedBy(@PathVariable String companyNum){

        List<EntityModel<TaskViewDto>> tasks = this.taskService
                .findAllByAuthor(companyNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(tasks));

    }

    @GetMapping("/maintenance/{maintenanceNum}")
    public ResponseEntity<CollectionModel<EntityModel<TaskViewDto>>> tasksInMaintenance(@PathVariable String maintenanceNum){

        List<EntityModel<TaskViewDto>> tasks = this.taskService
                .findAllAddedInMaintenance(maintenanceNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(tasks));

    }


}
