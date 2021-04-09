package managing.tool.e_task.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskStatusEnum;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
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
    private final ServiceUtil utils;
    private final Random random;
    private final ServiceUtil serviceUtil;

    @Cacheable("tasks")
    @Override
    public List<TaskViewDto> findAllTasks() {
        return this.taskRepository
                .findAll()
                .stream()
                .map(this::buildTaskVMRelationalStrings)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "tasks", allEntries = true)
    public void evictCachedTasks(){

    }

    @Override
    public List<MaintenanceViewDto> findAllMaintenanceByTask(String taskNum) {
        return this.taskRepository.findByTaskNum(taskNum)
                .getMaintenances()
                .stream()
                .map(maintenanceEntity -> {
                    MaintenanceViewDto maintenanceViewModel = this.modelMapper.map(maintenanceEntity, MaintenanceViewDto.class);
                    maintenanceViewModel.setFacility(maintenanceEntity.getFacility().getName())
                            .setAircraftRegistration(maintenanceEntity.getAircraft().getAircraftRegistration())
                            .setResponsibleEngineer(this.serviceUtil.userViewStringBuild(maintenanceEntity.getResponsibleEngineer())
                            );

                    return maintenanceViewModel;
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<TaskViewDto> findAllByAuthor(String companyNum) {

        return this.taskRepository
                .findAllByPreparedByContains(this.userService.findByCompanyNum(companyNum))
                .stream()
                .map(this::buildTaskVMRelationalStrings)
                .collect(Collectors.toList());
    }

    public String createPrepTeamString(TaskEntity taskEntity){
        StringBuilder preparedBy = new StringBuilder();
        taskEntity.getPreparedBy()
                .forEach(u -> preparedBy.append(this.serviceUtil.userViewStringBuild(u))
                                        .append(", "));

        return preparedBy.toString(); //.replaceAll(", $", "");
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
                .map(this::buildTaskVMRelationalStrings)
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
    public TaskEntity getRandomTask() {
        long maxRandomNumber = this.taskRepository.count();
        long randomId = this.random.nextInt((int) maxRandomNumber);

        return this.taskRepository.getOne(randomId);
    }


    @Override
    public TaskViewDto findTask(String taskNum) {
        return this.modelMapper.map(
                this.taskRepository.findByTaskNum(taskNum),
                TaskViewDto.class
        );

    }

    @Override
    public TaskViewDto updateTask(TaskViewDto taskDataForUpdate, String token) {
        TaskEntity taskToUpdate = this.modelMapper.map(taskDataForUpdate, TaskEntity.class);

        TaskEntity taskExisting = this.taskRepository.findByTaskNum(taskDataForUpdate.getTaskNum());
        Set<UserEntity> updatedPrepTeam = preparingTeam(taskExisting.getPreparedBy(), token);

        taskToUpdate.setPreparedBy(updatedPrepTeam)
                .setId(taskExisting.getId())
                .setUpdatedOn(LocalDateTime.now());

        return this.modelMapper.map(this.taskRepository.save(taskToUpdate), TaskViewDto.class);
    }


    @Override
    public TaskViewDto createTask(TaskViewDto taskNew, String token) {
        TaskEntity taskToCreate = this.modelMapper.map(taskNew, TaskEntity.class);

        Set<UserEntity> creatingTeam = preparingTeam(new HashSet<>(), token);

        taskToCreate.setPreparedBy(creatingTeam)
                .setCreatedOn(LocalDateTime.now());

        return this.modelMapper.map(this.taskRepository.save(taskToCreate), TaskViewDto.class);
    }


    private Set<UserEntity> preparingTeam(Set<UserEntity> taskPrepTeam,String token) {
        taskPrepTeam.add(this.utils.identifyingUserFromToken(token));

        return taskPrepTeam;
    }

    private TaskViewDto buildTaskVMRelationalStrings(TaskEntity taskEntity) {
        TaskViewDto mappedTask = this.modelMapper.map(taskEntity, TaskViewDto.class);
        mappedTask.setPreparedBy(createPrepTeamString(taskEntity));
        mappedTask.setStatus(createCorrectStatus(taskEntity));
        return mappedTask;
    }

}
