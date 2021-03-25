package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskStatusEnum;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

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
                    TaskViewDto mappedTask = this.modelMapper.map(t, TaskViewDto.class);
                    mappedTask.setPreparedBy(allocatePrepTeam(t));
                    mappedTask.setStatus(allocateStatus(t));
                    return mappedTask;
                } )
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskViewDto> findAllByAuthor(String companyNum) {

        return this.taskRepository
                .findAllByPreparedByContains(this.userService.findByCompanyNum(companyNum))
                .stream()
                .map(t -> {
                    TaskViewDto mappedTask = this.modelMapper.map(t, TaskViewDto.class);
                    mappedTask.setPreparedBy(allocatePrepTeam(t));
                    mappedTask.setStatus(allocateStatus(t));
                    return mappedTask;
                })
                .collect(Collectors.toList());
    }

    public String allocatePrepTeam(TaskEntity taskEntity){
        StringBuilder preparedBy = new StringBuilder();
        taskEntity.getPreparedBy()
                .stream()
                .forEach(u -> preparedBy.append(String.format("%s - %s",u.getCompanyNum(), u.getLastName())));

        return preparedBy.toString();
    }

    public String allocateStatus(TaskEntity taskEntity){
        return String.valueOf(taskEntity.isJobcardsPrepared()
                                    && taskEntity.isQualityAssured()
                                    && taskEntity.isToolingAvailable()
                                    ? TaskStatusEnum.OK
                                    : TaskStatusEnum.NOT_OK);
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
    public TaskEntity findTaskByTaskNumber(String taskNum) {
        return this.taskRepository.findByTaskNum(taskNum);
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
    public Boolean taskExists(String taskNum) {
        System.out.println();
        return this.findTaskByTaskNumber(taskNum) != null;
    }



    @Override
    public TaskViewDto findTask(String taskNum) {
        return this.modelMapper.map(
                this.taskRepository.findByTaskNum(taskNum),
                TaskViewDto.class
        );

    }

    @Override
    public TaskViewDto updateTask(TaskViewDto taskViewDto) {
        TaskEntity taskEntity = this.modelMapper.map(taskViewDto, TaskEntity.class);

        TaskEntity existingTask = this.taskRepository.findByTaskNum(taskViewDto.getTaskNum());

        taskEntity.setId(existingTask.getId());
        taskEntity.setUpdatedOn(Instant.now());

        return this.modelMapper.map(this.taskRepository.save(taskEntity), TaskViewDto.class);
    }

    @Override
    public TaskViewDto createTask(TaskViewDto taskViewDto) {
        return null;
    }
}
