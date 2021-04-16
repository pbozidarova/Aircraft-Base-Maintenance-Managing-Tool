package managing.tool.e_notification.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_base.BaseEntity;
import managing.tool.e_notification.model.NotificationClassificationEnum;
import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_notification.model.NotificationStatusEnum;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.model.dto.ReplyResponseDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import managing.tool.e_notification.repository.NotificationRepository;
import managing.tool.e_notification.service.CloudinaryService;
import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.service.NotificationValidationService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.GlobalUncaughtExceptionAdvice;
import managing.tool.exception.NotFoundInDb;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.NOTFOUNDERROR;
import static managing.tool.constants.GlobalConstants.NOTFOUND_SELECT_ERROR;

@Service
@Transactional
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final NotificationValidationService notificationValidationService;
    private final UserService userService;
    private final TaskService taskService;
    private final MaintenanceService maintenanceService;
    private final ModelMapper modelMapper;
    private final ReplyService replyService;
    private final Random random;
    private final CloudinaryService cloudinaryService;

    private static Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);

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

                    String notificationNum = notificationNumberBuilder(
                            this.notificationRepository.count()+1,
                            randomMaintenance.getMaintenanceNum(),
                            randomTask.getTaskNum());

                    notificationEntity.setNotificationNum(notificationNum)
                                      .setReplies(communication)
                                      .setAuthor(this.userService.getRandomUser())
                                      .setStatus(randomStatus)
                                      .setMaintenance(randomMaintenance)
                                      .setTask(randomTask)
                                      .setDueDate(LocalDate.now().plusDays(3))
                                      .setCreatedOn(LocalDateTime.now());

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

    @Cacheable("notifications")
    @Override
    public List<NotificationViewDto> findAllNotifications() {
        return this.notificationRepository
                .findAll()
                .stream()
                .map(this::buildNotifView)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "notifications", allEntries = true)
    public void evictCachedNotifications(){

    }

    @Override
    @Scheduled(cron = "@daily")
//    @Scheduled(cron = "*/2 * * * * *") // used for testing
    public void defineOverdueNotifications() {
        StringBuilder notificationsOverdueLog = new StringBuilder();

        AtomicInteger count = new AtomicInteger();
        this.notificationRepository.findAll()
                .forEach(notification -> {
                    boolean dueDateHasPassed = notification.getDueDate().compareTo(LocalDate.now()) < 0;
                    if(dueDateHasPassed){
                        count.getAndIncrement();
                        notificationsOverdueLog.append( String.format("Notification with number %s is overdue. Its due date was %s!" , notification.getNotificationNum(), notification.getDueDate()))
                                                .append(System.lineSeparator());
                    }
                });

        notificationsOverdueLog.append(String.format("A total of %s notification(s) are overdue!", count));

        LOGGER.info(notificationsOverdueLog.toString());
    }

    private NotificationViewDto buildNotifView(NotificationEntity notificationEntity){
        NotificationViewDto notificationViewDto = this.modelMapper.map(notificationEntity, NotificationViewDto.class);

        notificationViewDto
                .setAuthor(this.userService.userViewStringBuild(notificationEntity.getAuthor()));

        return notificationViewDto;
    }

    @Override
    public List<NotificationViewDto> findAllNotificationsByAuthor(String companyNum) {
        UserEntity userEntity = this.userService.findByCompanyNum(companyNum);

        return this.notificationRepository.findAllByAuthor(userEntity)
                .stream()
                .map(this::buildNotifView)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllNotificationByMaintenance(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceService.findByMaintenanceNum(maintenanceNum);

        return this.notificationRepository.findAllByMaintenance(maintenanceEntity)
                .stream()
                .map(this::buildNotifView)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationViewDto> findAllNotifForTask(String taskNum) {
        TaskEntity taskEntity = this.taskService.findTaskByTaskNumber(taskNum);

        return this.notificationRepository.findAllByTask(taskEntity)
                .stream()
                .map(this::buildNotifView)
                .collect(Collectors.toList());

    }

    @Override
    public NotificationEntity findByNotificationNum(String notificationNum) {
        return this.notificationRepository.findByNotificationNum(notificationNum);
    }

    @Override
    public Boolean notificationExists(String notificationNum) {
        return this.findByNotificationNum(notificationNum) != null;
    }

    @Override
    public Integer openNotificationsOfLoggedInUser(String token) {
        UserEntity userEntity = this.userService.identifyingUserFromToken(token);

        return this.notificationRepository
                .countNotificationEntitiesByAuthorAndStatus(userEntity, NotificationStatusEnum.OPENED)
              +
                this.notificationRepository.countNotificationEntitiesByAuthorAndStatus(userEntity, NotificationStatusEnum.PROGRESSING);
    }

    @Override
    public NotificationViewDto updateNotification(String notificationNum, NotificationViewDto notificationDataForUpdate) {
        if(!this.notificationExists(notificationNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, notificationNum), "notificationNum");
        }

        //VALIDATE SELECT VALUES!
        notificationValidationService.validateIfMaintenanceExists(notificationDataForUpdate.getMaintenanceNum());
        notificationValidationService.validateIfTaskExists(notificationDataForUpdate.getTaskNum());
        notificationValidationService.validateStatus(notificationDataForUpdate.getStatus());
        notificationValidationService.validateClassification(notificationDataForUpdate.getClassification());

        NotificationEntity notificationToUpdate = this.notificationRepository.findByNotificationNum(notificationDataForUpdate.getNotificationNum());

        String notificationNumber = notificationNumberBuilder(
                Long.parseLong(notificationDataForUpdate.getNotificationNum().split("_")[0].replace("N", "")),
                notificationDataForUpdate.getMaintenanceNum(),
                notificationDataForUpdate.getTaskNum());

        notificationToUpdate.setNotificationNum(notificationNumber);

        buildNotificationEntity(notificationToUpdate, notificationDataForUpdate);
        return  this.modelMapper.map(this.notificationRepository.save(notificationToUpdate), NotificationViewDto.class);
    }


    @Override
    public NotificationViewDto createNotification(NotificationViewDto notificationNewData, String token) {
        //VALIDATE SELECT VALUES!
        notificationValidationService.validateIfMaintenanceExists(notificationNewData.getMaintenanceNum());
        notificationValidationService.validateIfTaskExists(notificationNewData.getTaskNum());
        notificationValidationService.validateStatus(notificationNewData.getStatus());
        notificationValidationService.validateClassification(notificationNewData.getClassification());

        NotificationEntity notificationToCreate = new NotificationEntity();
        UserEntity author = this.userService.identifyingUserFromToken(token);
        notificationToCreate.setAuthor(author);

        String newNotificationNumber = notificationNumberBuilder(
                this.notificationRepository.count()+1,
                notificationNewData.getMaintenanceNum(),
                notificationNewData.getTaskNum()
        );
        notificationToCreate.setNotificationNum(newNotificationNumber);
        buildNotificationEntity(notificationToCreate, notificationNewData);

        return this.modelMapper.map(this.notificationRepository.save(notificationToCreate), NotificationViewDto.class);
    }

    @Override
    public List<ReplyViewDto> getCommunication(String notificationNum) {
        return this.notificationRepository
                        .findByNotificationNum(notificationNum)
                        .getReplies()
                        .stream()
                        .sorted(Comparator.comparing(BaseEntity::getCreatedOn))
                        .map(reply -> {
                            ReplyViewDto replyViewDto = this.modelMapper.map(reply, ReplyViewDto.class);

                            replyViewDto.setAuthor(this.userService.userViewStringBuild(reply.getAuthor()))
                                        .setCreatedOn(reply.getCreatedOn().toString().replace("T", " "))
                                        .setTitle(reply.getAuthor().getRoles().contains(RoleEnum.ADMIN)
                                                            ? RoleEnum.ADMIN.toString()
                                                            : RoleEnum.MECHANIC.toString());
                            return replyViewDto;
                        }).collect(Collectors.toList());
    }

    @Override
    public ReplyViewDto createReply(String notificationNum, String jwt, String reply, MultipartFile attachment) throws IOException {
        if(!this.notificationExists(notificationNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, notificationNum), "notificationNum");
        }

        ReplyEntity replyToCreate = new ReplyEntity();

        String url = cloudinaryService.uploadImage(attachment);

        UserEntity author = this.userService.identifyingUserFromToken(jwt);
        replyToCreate.setDescription(reply)
                .setAuthor(author)
                .setAttachment(url)
                .setCreatedOn(LocalDateTime.now());

        ReplyEntity replySaved = this.replyService.saveReply(replyToCreate);
        this.notificationRepository.findByNotificationNum(notificationNum).getReplies().add(replySaved);

        return this.modelMapper.map(reply, ReplyViewDto.class);
    }

    private void buildNotificationEntity(NotificationEntity notification, NotificationViewDto notificationViewDto) {
        MaintenanceEntity maintenanceEntity = this.maintenanceService.findByMaintenanceNum(notificationViewDto.getMaintenanceNum());
        TaskEntity taskEntity = this.taskService.findTaskByTaskNumber(notificationViewDto.getTaskNum());
        LocalDate dueDateToUpdate = LocalDate.parse(notificationViewDto.getDueDate(), DateTimeFormatter.ofPattern("yyyy-M-dd"));
        NotificationStatusEnum status = NotificationStatusEnum.valueOf(notificationViewDto.getStatus());

        notification.setMaintenance(maintenanceEntity)
                .setTask(taskEntity)
                .setStatus(status)
                .setDueDate( dueDateToUpdate )
                .setClassification(null)
                .setUpdatedOn(LocalDateTime.now());

        if(status.equals(NotificationStatusEnum.CLOSED)) {
            NotificationClassificationEnum classification = NotificationClassificationEnum.valueOf(notificationViewDto.getClassification());
            notification.setClassification(classification);
        }

    }

    private String notificationNumberBuilder(long count, String maintenanceNum, String taskNum){
        return String.format("N%s_%s_%s", count, maintenanceNum, taskNum);
    }
}
