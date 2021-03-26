package managing.tool.e_task.service;

import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_user.model.dto.UserViewDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface TaskService {

    TaskViewDto findTask(String taskNum);
    TaskViewDto updateTask(TaskViewDto taskViewDto, String jwt);
    TaskViewDto createTask(TaskViewDto taskViewDto, String jwt);

    List<TaskViewDto> findAllTasks();


    //HATEOAS functions
    List<TaskViewDto> findAllByAuthor(String companyNum);
    List<TaskViewDto> findAllAddedInMaintenance(String maintenanceNum);
    List<MaintenanceViewDto> findAllMaintenanceByTask(String taskNum);

    TaskEntity findTaskByTaskNumber(String taskNum);
    Set<TaskEntity> getRandomTaskList();


    Boolean taskExists(String taskNum);
}
