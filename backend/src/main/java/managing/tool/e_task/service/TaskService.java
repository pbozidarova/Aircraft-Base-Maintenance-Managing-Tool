package managing.tool.e_task.service;

import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskRequestDto;
import managing.tool.e_task.model.dto.TaskViewDto;

import java.util.List;

public interface TaskService {

    TaskViewDto findTask(String taskNum);
    TaskViewDto updateTask(String taskNum, TaskRequestDto taskViewDto, String jwt);
    TaskViewDto createTask(String taskNum, TaskRequestDto taskViewDto, String jwt);

    List<TaskViewDto> findAllTasks();


    //HATEOAS functions
    List<TaskViewDto> findAllByAuthor(String companyNum);
    List<TaskViewDto> findAllAddedInMaintenance(String maintenanceNum);
    List<MaintenanceViewDto> findAllMaintenanceByTask(String taskNum);

    TaskEntity findTaskByTaskNumber(String taskNum);



    Boolean taskExists(String taskNum);

    TaskEntity getRandomTask();

    void evictCachedTasks();
}
