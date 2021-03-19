package managing.tool.e_task.service;

import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface TaskService {

    List<TaskViewDto> findAllTasks();
    List<TaskViewDto> findByCreatedBy(String companyNum);
    List<TaskViewDto> findByAddedInMaintenance(String maintenanceNum);

    void seedTasks() throws FileNotFoundException;
    boolean areTasksUploaded();
    Set<TaskEntity> getRandomTaskList();

}
