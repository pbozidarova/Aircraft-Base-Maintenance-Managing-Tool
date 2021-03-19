package managing.tool.e_task.service;

import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface TaskService {

    List<TaskViewDto> findAllTasks();
    List<TaskViewDto> findAllByAuthor(String companyNum);
    List<TaskViewDto> findAllAddedInMaintenance(String maintenanceNum);
    Set<TaskEntity> getRandomTaskList();

    TaskViewDto findTask(String taskNum);
}
