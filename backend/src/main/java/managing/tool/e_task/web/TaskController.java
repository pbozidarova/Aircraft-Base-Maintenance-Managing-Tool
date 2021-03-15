package managing.tool.e_task.web;

import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.dto.UserViewDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/{companyNum}")
//    public UserViewDto user(@PathVariable String companyNum){
//        return this.userService.findUser(companyNum);
//    }
}
