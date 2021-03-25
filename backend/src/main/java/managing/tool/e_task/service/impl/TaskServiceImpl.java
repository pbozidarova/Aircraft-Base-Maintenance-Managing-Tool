package managing.tool.e_task.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskStatusEnum;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.util.JwtUtil;
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
    private final JwtUtil jwtUtil;

    @Override
    public List<TaskViewDto> findAllTasks() {
        return this.taskRepository
                .findAll()
                .stream()
                .map(t -> {
                    TaskViewDto mappedTask = this.modelMapper.map(t, TaskViewDto.class);
                    mappedTask.setPreparedBy(createPrepTeamString(t));
                    mappedTask.setStatus(createCorrectStatus(t));
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
                    mappedTask.setPreparedBy(createPrepTeamString(t));
                    mappedTask.setStatus(createCorrectStatus(t));
                    return mappedTask;
                })
                .collect(Collectors.toList());
    }

    public String createPrepTeamString(TaskEntity taskEntity){
        StringBuilder preparedBy = new StringBuilder();
        taskEntity.getPreparedBy()
                .stream()
                .forEach(u -> preparedBy.append(String.format("%s - %s, ",u.getCompanyNum(), u.getLastName())));

        return preparedBy.toString().replaceAll(", $", "");
    }

    public String createCorrectStatus(TaskEntity taskEntity){
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
    public TaskViewDto updateTask(TaskViewDto taskViewDto, String token) {
        TaskEntity taskToUpdate = this.modelMapper.map(taskViewDto, TaskEntity.class);

        TaskEntity existingTask = this.taskRepository.findByTaskNum(taskViewDto.getTaskNum());
        taskToUpdate.setId(existingTask.getId());
        taskToUpdate.setUpdatedOn(Instant.now());

        Set<UserEntity> updatedPrepTeam = preparingTeam(existingTask.getPreparedBy(), token);
        taskToUpdate.setPreparedBy(updatedPrepTeam);

        return this.modelMapper.map(this.taskRepository.save(taskToUpdate), TaskViewDto.class);
    }


    @Override
    public TaskViewDto createTask(TaskViewDto taskViewDto, String token) {
        TaskEntity taskToCreate = this.modelMapper.map(taskViewDto, TaskEntity.class);
        taskToCreate.setUpdatedOn(Instant.now());

        Set<UserEntity> creatingTeam = preparingTeam(new HashSet<>(), token);
        taskToCreate.setPreparedBy(creatingTeam);

        return this.modelMapper.map(this.taskRepository.save(taskToCreate), TaskViewDto.class);
    }

    public UserEntity userCreatingTheChange(String token){
        String companyNum = this.jwtUtil.extractUsername(token.replace("Bearer ", ""));
        return this.userService.findByCompanyNum(companyNum);
    }

    private Set<UserEntity> preparingTeam(Set<UserEntity> taskPrepTeam,String token) {
        taskPrepTeam.add(userCreatingTheChange(token));

        return taskPrepTeam;
    }

}
