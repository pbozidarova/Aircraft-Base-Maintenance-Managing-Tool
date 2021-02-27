package managing.tool.service.impl;

import com.google.gson.Gson;
import managing.tool.model.dto.seed.TaskSeedDto;
import managing.tool.model.entity.Maintenance;
import managing.tool.model.entity.Task;
import managing.tool.repository.TaskRepository;
import managing.tool.service.AircraftService;
import managing.tool.service.MaintenanceService;
import managing.tool.service.TaskService;
import managing.tool.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static managing.tool.constants.GlobalConstants.TASKS_MOCK_DATA_PATH;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Random random;

    public TaskServiceImpl(TaskRepository taskRepository, ModelMapper modelMapper, Gson gson, Random random) {
        this.taskRepository = taskRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.random = random;
    }

    @Override
    public void seedTasks() throws FileNotFoundException {
        if(areTasksUploaded()){
            return;
        }

        TaskSeedDto[] dtos = this.gson.fromJson(
                new FileReader(TASKS_MOCK_DATA_PATH), TaskSeedDto[].class
        );

        Arrays.stream(dtos)
                .forEach(tDto -> {
                    Task task = this.modelMapper.map(tDto, Task.class);
                    this.taskRepository.save(task);
                });
    }

    @Override
    public boolean areTasksUploaded() {

        return this.taskRepository.count() > 0;
    }

    @Override
    public Set<Task> getRandomTaskList() {
        Set<Task> randomTaskList = new HashSet<>();
        int maxRandomNumber = (int) this.taskRepository.count() / 5; //below 400
        int randomListLength = this.random.nextInt(maxRandomNumber);

        for (int i = 0; i < randomListLength; i++) {
            long randomId = random.nextInt(maxRandomNumber) + 1;
            Task task = this.taskRepository.getOne(randomId);
            randomTaskList.add(task);
        }
        return randomTaskList;
    }
}
