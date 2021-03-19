package managing.tool.e_task.service;

import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

public interface TaskSeedService {

    void seedTasks() throws FileNotFoundException;
    boolean areTasksUploaded();


}
