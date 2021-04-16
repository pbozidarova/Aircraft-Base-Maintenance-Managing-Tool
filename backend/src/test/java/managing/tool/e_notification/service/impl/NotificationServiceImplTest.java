package managing.tool.e_notification.service.impl;

import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_notification.model.NotificationStatusEnum;
import managing.tool.e_notification.model.ReplyEntity;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.model.dto.ReplyViewDto;
import managing.tool.e_notification.repository.NotificationRepository;
import managing.tool.e_notification.service.CloudinaryService;
import managing.tool.e_notification.service.NotificationValidationService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static managing.tool.e_user.service.impl.UserMockValues.ADMIN_STRING;
import static managing.tool.e_user.service.impl.UserMockValues.ENGINEER_STRING;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {
    private final String NOTIFICATION_NUM = "N1_M123_T321";
    private final String MAINTENANCE_NUM = "M123";
    private final String TASK_NUM = "N321";
    private final String STATUS_CLOSED = "CLOSED";
    private final String STATUS_OPENED = "OPENED";
    private final String CLASSIFICATION = "REFUSED";
    private final String JWT_STRING = "jwt";
    private final String REPLY_STRING = "Very long notification reply";
    private final String AUTHOR_STRING = "N123 - LN, FN";
    private final String ATTACHMENT_URL = "http://url";

    private NotificationServiceImpl testService;
    private NotificationEntity notificationToBeSaved;
    private NotificationEntity notificationExisting;
    private NotificationViewDto notificationRequest;
    private NotificationViewDto notificationRequestCreate;
    private MaintenanceEntity maintenanceEntity;
    private TaskEntity taskEntity;
    private MultipartFile attachment;
    private UserEntity userEntity;
    private ReplyEntity replyEntity;
    private ReplyViewDto replyRequest;

    @Mock
    NotificationValidationService mockedNotificationValidationService;
    @Mock
    NotificationRepository mockedNotificationRepository;
    @Mock
    UserService mockedUserService;
    @Mock
    TaskService mockedTaskService;
    @Mock
    MaintenanceService mockedMaintenanceService;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    ReplyService mockedReplyService;
    @Mock
    Random mockedRandom;
    @Mock
    CloudinaryService mockedCloudinaryService;

    @BeforeEach
    void setUp() {
        testService = new NotificationServiceImpl(mockedNotificationRepository,
                mockedNotificationValidationService,
                mockedUserService,
                mockedTaskService,
                mockedMaintenanceService,
                mockedModelMapper,
                mockedReplyService,
                mockedRandom,
                mockedCloudinaryService);

        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity role = new RoleEntity();
        RoleEntity authority = new RoleEntity();
        role.setName(RoleEnum.valueOf(ADMIN_STRING));
        authority.setName(RoleEnum.valueOf(ENGINEER_STRING));

        roles.add(role);
        roles.add(authority);

        userEntity = new UserEntity();
        userEntity.setCompanyNum(AUTHOR_STRING)
                .setFirstName(AUTHOR_STRING)
                .setLastName(AUTHOR_STRING)
                .setRoles(roles);

        replyEntity = new ReplyEntity();
        replyEntity.setDescription(REPLY_STRING)
                .setAuthor(userEntity)
                .setCreatedOn(LocalDateTime.now());
        replyRequest = new ReplyViewDto();
        replyRequest.setDescription(REPLY_STRING);

        notificationExisting = new NotificationEntity();
        notificationExisting.setNotificationNum(NOTIFICATION_NUM)
                .setStatus(NotificationStatusEnum.valueOf(STATUS_OPENED))
                .setAuthor(userEntity)
                .setReplies(Set.of(replyEntity));

        notificationToBeSaved = new NotificationEntity();
        notificationToBeSaved.setNotificationNum(NOTIFICATION_NUM);

        notificationRequest = new NotificationViewDto();
        notificationRequest.setNotificationNum(NOTIFICATION_NUM)
                .setDueDate("2021-04-14")
                .setMaintenanceNum(MAINTENANCE_NUM)
                .setTaskNum(TASK_NUM)
                .setStatus(STATUS_CLOSED)
                .setClassification(CLASSIFICATION);

        notificationRequestCreate = new NotificationViewDto();
        notificationRequestCreate.setDueDate("2021-04-14")
                .setMaintenanceNum(MAINTENANCE_NUM)
                .setTaskNum(TASK_NUM)
                .setStatus(STATUS_CLOSED)
                .setClassification(CLASSIFICATION);


        maintenanceEntity = new MaintenanceEntity();
        taskEntity = new TaskEntity();

    }

    @Test
    void generateMockupNotificationsOnInitialLaunch(){

        Mockito.when(mockedReplyService.findAll()).thenReturn(List.of(replyEntity));
        Mockito.when(mockedMaintenanceService.getRandomMaintenance()).thenReturn(maintenanceEntity);
        Mockito.when(mockedTaskService.getRandomTask()).thenReturn(taskEntity);
        Mockito.when(mockedNotificationRepository.count()).thenReturn(0L);
        Mockito.when(mockedUserService.getRandomUser()).thenReturn(userEntity);

        Mockito.when(mockedNotificationRepository.save(notificationToBeSaved)).thenReturn(notificationToBeSaved);

        testService.generateMockupNotificationsOnInitialLaunch();
        System.out.println(mockedNotificationRepository.count());

        Assertions.assertTrue(mockedNotificationRepository.count() == 1);
    }

    @Test
    void getCommunicationTest(){
        Mockito.when(mockedNotificationRepository.findByNotificationNum(NOTIFICATION_NUM)).thenReturn(notificationExisting);
        Mockito.when(mockedModelMapper.map(replyEntity, ReplyViewDto.class)).thenReturn(replyRequest);
        Mockito.when(mockedUserService.userViewStringBuild(userEntity)).thenReturn(AUTHOR_STRING);

        Assertions.assertTrue(testService.getCommunication(NOTIFICATION_NUM).size() == 1);
    }


    @Test
    void notificationDoesNotExistUpdate() {
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.updateNotification(NOTIFICATION_NUM, notificationRequest);
                });
    }

    @Test
    void updateNotificationTest() {
        Mockito.when(mockedNotificationRepository.findByNotificationNum(NOTIFICATION_NUM)).thenReturn(notificationToBeSaved);
        Mockito.when(mockedNotificationRepository.save(notificationToBeSaved)).thenReturn(notificationToBeSaved);
        Mockito.when(mockedModelMapper.map(notificationToBeSaved, NotificationViewDto.class)).thenReturn(notificationRequest);

        NotificationViewDto notificationUpdated = testService.updateNotification(NOTIFICATION_NUM, notificationRequest);

        Assertions.assertEquals(NOTIFICATION_NUM, notificationUpdated.getNotificationNum());
        Assertions.assertTrue(!notificationUpdated.getStatus().equals(STATUS_OPENED));
    }

//    @Test
//    void createNotificationTest() {
//        Mockito.when(mockedUserService.identifyingUserFromToken(JWT_STRING)).thenReturn(userEntity);
//        Mockito.when(mockedNotificationRepository.count()).thenReturn(0L);
//        Mockito.when(mockedMaintenanceService.findByMaintenanceNum(MAINTENANCE_NUM)).thenReturn(maintenanceEntity);
//        Mockito.when(mockedTaskService.findTaskByTaskNumber(TASK_NUM)).thenReturn(taskEntity);
//
//        Mockito.when(mockedNotificationRepository.save(notificationToBeSaved)).thenReturn(notificationToBeSaved);
//        Mockito.when(mockedModelMapper.map(notificationToBeSaved, NotificationViewDto.class)).thenReturn(notificationRequest);
//
//        NotificationViewDto notificationCreate = testService.createNotification(notificationRequest, JWT_STRING);
//
//        Assertions.assertEquals(MAINTENANCE_NUM, notificationCreate.getMaintenanceNum());
//    }


    @Test
    void notificationDoesNotExistCreate() {
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    //String notificationNum, String jwt, String reply, MultipartFile attachment
                    testService.createReply(NOTIFICATION_NUM, JWT_STRING, REPLY_STRING, attachment);
                });
    }



    @Test
    void findAllNotifForTaskTest() {
        Mockito.when(mockedModelMapper.map(notificationExisting, NotificationViewDto.class)).thenReturn(notificationRequest);
        Mockito.when(mockedUserService.userViewStringBuild(userEntity)).thenReturn(AUTHOR_STRING);

        Mockito.when(mockedTaskService.findTaskByTaskNumber(TASK_NUM)).thenReturn(taskEntity);
        Mockito.when(mockedNotificationRepository.findAllByTask(taskEntity)).thenReturn(List.of(notificationExisting));

        Assertions.assertTrue(testService.findAllNotifForTask(TASK_NUM).size() == 1);

    }

    @Test
    void findAllNotificationsByAuthorTest() {
        Mockito.when(mockedUserService.findByCompanyNum(AUTHOR_STRING)).thenReturn(userEntity);
        Mockito.when(mockedUserService.userViewStringBuild(userEntity)).thenReturn(AUTHOR_STRING);
        Mockito.when(mockedNotificationRepository.findAllByAuthor(userEntity)).thenReturn(List.of(notificationExisting));
        Mockito.when(mockedModelMapper.map(notificationExisting, NotificationViewDto.class)).thenReturn(notificationRequest);

        Assertions.assertTrue(testService.findAllNotificationsByAuthor(AUTHOR_STRING).size() == 1);

    }

    @Test
    void findAllNotificationByMaintenanceTest() {
        Mockito.when(mockedMaintenanceService.findByMaintenanceNum(MAINTENANCE_NUM)).thenReturn(maintenanceEntity);
        Mockito.when(mockedUserService.userViewStringBuild(userEntity)).thenReturn(AUTHOR_STRING);

        Mockito.when(mockedNotificationRepository.findAllByMaintenance(maintenanceEntity)).thenReturn(List.of(notificationExisting));
        Mockito.when(mockedModelMapper.map(notificationExisting, NotificationViewDto.class)).thenReturn(notificationRequest);


        Assertions.assertTrue(testService.findAllNotificationByMaintenance(MAINTENANCE_NUM).size() == 1);
    }

    @Test
    void findAllNotificationsTest(){
        Mockito.when(mockedNotificationRepository.findAll()).thenReturn(List.of(notificationExisting));
        Mockito.when(mockedModelMapper.map(notificationExisting, NotificationViewDto.class)).thenReturn(notificationRequest);

        Assertions.assertTrue(testService.findAllNotifications().size() == 1);

    }

    @Test
    void openNotificationsOfLoggedInUser() {
        Mockito.when(mockedUserService.identifyingUserFromToken(JWT_STRING)).thenReturn(userEntity);
        Mockito.when(mockedNotificationRepository.countNotificationEntitiesByAuthorAndStatus(userEntity, NotificationStatusEnum.OPENED)).thenReturn(2);
        Mockito.when(mockedNotificationRepository.countNotificationEntitiesByAuthorAndStatus(userEntity, NotificationStatusEnum.PROGRESSING)).thenReturn(3);

        Assertions.assertTrue(testService.openNotificationsOfLoggedInUser(JWT_STRING) == 5);
    }

//    @Test
//    void createReplyTest() throws IOException {
//        Mockito.when(mockedCloudinaryService.uploadImage(attachment)).thenReturn(ATTACHMENT_URL);
//        Mockito.when(mockedUserService.identifyingUserFromToken(JWT_STRING)).thenReturn(userEntity);
//        Mockito.when(mockedReplyService.saveReply(replyEntity)).thenReturn(replyEntity);
//        Mockito.when(mockedNotificationRepository.findByNotificationNum(NOTIFICATION_NUM)).thenReturn(notificationExisting);
//        Mockito.when(mockedModelMapper.map(replyEntity, ReplyViewDto.class)).thenReturn(replyRequest);
//
////        String notificationNum, String jwt, String reply, MultipartFile attachment
//        ReplyViewDto replyCreated = testService.createReply(NOTIFICATION_NUM, JWT_STRING, REPLY_STRING, attachment);
//
////        Mockito.verify(mockedReplyService).saveReply(replyEntity);
//
//        Assertions.assertEquals(replyCreated.getDescription(), REPLY_STRING);
//    }

}