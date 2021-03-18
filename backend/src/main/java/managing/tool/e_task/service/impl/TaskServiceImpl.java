package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
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
@Transactional
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final Random random;

    public TaskServiceImpl(TaskRepository taskRepository, UserService userService, ModelMapper modelMapper, Gson gson, Random random) {
        this.taskRepository = taskRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.random = random;
    }

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
    public List<TaskViewDto> findByCreatedBy(String companyNum) {

        return this.taskRepository
                .findAllByPreparedByContainsOrderByUpdatedOn(this.userService.findByCompanyNum(companyNum))
                .stream()
                .map(t -> this.modelMapper.map(t, TaskViewDto.class))
                .collect(Collectors.toList());
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
                    TaskEntity task = this.modelMapper.map(tDto, TaskEntity.class);

                    UserEntity userEntity = this.userService.getRandomUser();

                    task.setPreparedBy(new HashSet<>());
                    task.getPreparedBy().add(userEntity);


                    task.setToolingAvailable(this.random.nextBoolean());
                    task.setAreJobCardsPrepared(this.random.nextBoolean());
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
