package managing.tool.e_task.web;

import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.FRONTEND_URL;

@RestController
@RequestMapping("/tasks")
@CrossOrigin(FRONTEND_URL)
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("all")
    public List<TaskViewDto> allTasks(){
        return this.taskService.findAllTasks();
    }

    @GetMapping("/{companyNum}")
    public ResponseEntity<CollectionModel<EntityModel<TaskViewDto>>> tasksPreparedBy(@PathVariable String companyNum){
        List<EntityModel<TaskViewDto>> tasks = this.taskService
                .findByCreatedBy(companyNum)
                .stream()
                .map(EntityModel::of)
                .collect(Collectors.toList());


        return ResponseEntity.ok(CollectionModel.of(tasks));

    }

//    @GetMapping("/{companyNum}")
//    public UserViewDto user(@PathVariable String companyNum){
//        return this.userService.findUser(companyNum);
//    }
}
