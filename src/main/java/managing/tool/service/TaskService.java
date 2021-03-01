package managing.tool.service;

import managing.tool.model.entity.TaskEntity;

import java.io.FileNotFoundException;
import java.util.Set;

public interface TaskService {
    void seedTasks() throws FileNotFoundException;
    boolean areTasksUploaded();
    Set<TaskEntity> getRandomTaskList();

}
