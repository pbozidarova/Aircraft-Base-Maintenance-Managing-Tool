package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskSeedDto;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_task.service.TaskSeedService;
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
@Transactional
@AllArgsConstructor
public class TaskSeedServiceImpl implements TaskSeedService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Random random;

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
                    TaskEntity task = this.modelMapper.map(tDto, TaskEntity.class);

                    UserEntity userEntity = this.userService.getRandomUser();

                    task.setPreparedBy(new HashSet<>());
                    task.getPreparedBy().add(userEntity);


                    task.setToolingAvailable(this.random.nextBoolean());
                    task.setJobcardsPrepared(this.random.nextBoolean());
                    task.setQualityAssured(this.random.nextBoolean());

                    this.taskRepository.save(task);
                });
    }

    @Override
    public boolean areTasksUploaded() {

        return this.taskRepository.count() > 0;
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
}
