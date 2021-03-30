package managing.tool.e_notification.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_notification.model.NotificationClassificationEnum;
import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_notification.model.NotificationStatusEnum;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import managing.tool.e_notification.repository.NotificationRepository;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final TaskService taskService;
    private final MaintenanceService maintenanceService;
    private final ModelMapper modelMapper;
    private final ReplyService replyService;
    private final Random random;
    private final ServiceUtil serviceUtil;

    @Override
    public void generateMockupNotificationsOnInitialLaunch() {
        if(notificationsExist()){
            return;
        }

        NotificationStatusEnum[] statusEnums = NotificationStatusEnum.values();
        int statusEnumLength = statusEnums.length;

        NotificationClassificationEnum[] classificationEnum = NotificationClassificationEnum.values();
        int classificationEnumLength = classificationEnum.length;

        this.replyService.findAll()
                .forEach(reply -> {
                    NotificationEntity notificationEntity = new NotificationEntity();

                    Set<ReplyEntity> communication = new HashSet<>();
                    communication.add(reply);

                    NotificationStatusEnum randomStatus = statusEnums[random.nextInt(statusEnumLength)];
                    MaintenanceEntity randomMaintenance = this.maintenanceService.getRandomMaintenance();
                    TaskEntity randomTask = this.taskService.getRandomTask();
                    String notificationNum = String.format("N%s_%s_%s",
                            this.notificationRepository.count()+1,
                            randomMaintenance.getMaintenanceNum(),
                            randomTask.getTaskNum());

                    notificationEntity.setNotificationNum(notificationNum)
                                      .setCommunication(communication)
                                      .setAuthor(this.userService.getRandomUser())
                                      .setStatus(randomStatus)
                                      .setMaintenance(randomMaintenance)
                                      .setTask(randomTask)
                                      .setDueDate(Instant.now().plus(3, ChronoUnit.DAYS))
                                      .setCreatedOn(Instant.now());

                    if(randomStatus.equals(NotificationStatusEnum.CLOSED)) {
                        NotificationClassificationEnum randomClassification = classificationEnum[random.nextInt(classificationEnumLength)];
                        notificationEntity.setClassification(randomClassification);
                    }
                    this.notificationRepository.save(notificationEntity);
                });
    }

    @Override
    public boolean notificationsExist() {
        return this.notificationRepository.count() > 0;
    }

    @Override
    public List<NotificationViewDto> findAllNotifications() {
        return this.notificationRepository
                .findAll()
                .stream()
                .map(notificationEntity -> {
                    NotificationViewDto notificationViewDto = this.modelMapper.map(notificationEntity, NotificationViewDto.class);

                    notificationViewDto
                            .setAuthor(this.serviceUtil.userViewStringBuild(notificationEntity.getAuthor()));

                    return notificationViewDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllNotificationsByAuthor(String companyNum) {
        UserEntity userEntity = this.userService.findByCompanyNum(companyNum);

        return this.notificationRepository.findAllByAuthor(userEntity)
                .stream()
                .map(i -> this.modelMapper.map(i, NotificationViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllNotificationByMaintenance(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceService.findByMaintenanceNum(maintenanceNum);

        return this.notificationRepository.findAllByMaintenance(maintenanceEntity)
                .stream()
                .map(i -> this.modelMapper.map(i, NotificationViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllNotifForTask(String taskNum) {
        TaskEntity taskEntity = this.taskService.findTaskByTaskNumber(taskNum);

        return this.notificationRepository.findAllByTask(taskEntity)
                .stream()
                .map(n -> this.modelMapper.map(n, NotificationViewDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public NotificationEntity findByNotificationNum(String notificationNum) {
        return this.notificationRepository.findByNotificationNum(notificationNum);
    }

    @Override
    public Boolean notificationExists(String notificationNum) {
        return  this.findByNotificationNum(notificationNum) != null;
    }

    @Override
    public NotificationViewDto updateNotification(NotificationViewDto notificationViewDto) {
        return null;
    }

    @Override
    public NotificationViewDto createNotification(NotificationViewDto notificationViewDto) {
        return null;
    }

    @Override
    public List<ReplyViewDto> getCommunication(String notificationNum) {
        return this.notificationRepository
                        .findAllByNotificationNum(notificationNum)
                        .stream()
                        .map(reply -> {
                            ReplyViewDto replyViewDto = this.modelMapper.map(reply, ReplyViewDto.class);

                            replyViewDto.setAuthor(this.serviceUtil.userViewStringBuild(reply.getAuthor()));

                            return replyViewDto;
                        }).collect(Collectors.toList());
    }


}
