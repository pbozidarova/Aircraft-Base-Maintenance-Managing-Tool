package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.dto.TaskSeedDto;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.TASKS_MOCK_DATA_PATH;

@Service
@AllArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final MaintenanceService maintenanceService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Random random;

    @Override
    public List<TaskViewDto> findAllTasks() {
        return this.taskRepository
                .findAll()
                .stream()
                .map(t -> {
                    TaskViewDto taskViewDto =  this.modelMapper.map(t, TaskViewDto.class);

                    return  taskViewDto;
                } )
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskViewDto> findAllByAuthor(String companyNum) {

        return this.taskRepository
                .findAllByPreparedByContains(this.userService.findByCompanyNum(companyNum))
                .stream()
                .map(t -> this.modelMapper.map(t, TaskViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskViewDto> findAllAddedInMaintenance(String maintenanceNum) {
        return this.taskRepository
                .findAllByMaintenancesContains(this.maintenanceService.findByMaintenanceNum(maintenanceNum))
                .stream()
                .map(t -> this.modelMapper.map(t, TaskViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Set<TaskEntity> getRandomTaskList() {
        Set<TaskEntity> randomTaskList = new HashSet<>();
        int maxRandomNumber = (int) this.taskRepository.count() / 5; //below 400
        int randomListLength = this.random.nextInt(maxRandomNumber);

        for (int i = 0; i < randomListLength; i++) {
            long randomId = random.nextInt(maxRandomNumber) + 1;
            TaskEntity task = this.taskRepository.getOne(randomId);
            randomTaskList.add(task);
        }
        return randomTaskList;
    }

    @Override
    public TaskViewDto findTask(String taskNum) {

        return this.modelMapper.map(
                this.taskRepository.findByTaskNum(taskNum),
                TaskViewDto.class
        );

    }
}
