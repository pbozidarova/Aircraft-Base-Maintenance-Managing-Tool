package managing.tool.e_task.service;

import managing.tool.e_task.model.TaskEntity;

import java.io.FileNotFoundException;
import java.util.Set;

public interface TaskService {
    void seedTasks() throws FileNotFoundException;
    boolean areTasksUploaded();
    Set<TaskEntity> getRandomTaskList();

}
