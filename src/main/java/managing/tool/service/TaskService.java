package managing.tool.service;

import managing.tool.model.entity.Maintenance;
import managing.tool.model.entity.Task;

import java.io.FileNotFoundException;
import java.util.Set;

public interface TaskService {
    void seedTasks() throws FileNotFoundException;
    boolean areTasksUploaded();
    Set<Task> getRandomTaskList();

}
