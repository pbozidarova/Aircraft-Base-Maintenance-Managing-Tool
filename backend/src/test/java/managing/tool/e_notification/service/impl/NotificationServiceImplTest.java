package managing.tool.e_notification.service.impl;

import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.model.NotificationEntity;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_notification.repository.NotificationRepository;
import managing.tool.e_notification.service.CloudinaryService;
import managing.tool.e_notification.service.NotificationValidationService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Random;

import static managing.tool.e_user.service.impl.UserMockValues.VALID_COMPANY_NUM;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {
    private final String NOTIFICATION_NUM = "N1_M123_T321";
    private final String MAINTENANCE_NUM = "M123";
    private final String TASK_NUM = "N321";
    private final String STATUS = "CLOSED";
    private final String CLASSIFICATION = "REFUSED";


    private NotificationServiceImpl testService;
    private NotificationEntity notificationToBeSaved;
    private NotificationEntity notificationExisting;
    private NotificationViewDto notificationRequest;

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
    void setUp(){
        testService = new NotificationServiceImpl(mockedNotificationRepository,
                mockedNotificationValidationService,
                mockedUserService,
                mockedTaskService,
                mockedMaintenanceService,
                mockedModelMapper,
                mockedReplyService,
                mockedRandom,
                mockedCloudinaryService);

        notificationExisting = new NotificationEntity();
        notificationExisting.setNotificationNum(NOTIFICATION_NUM);
        notificationToBeSaved = new NotificationEntity();

        notificationRequest = new NotificationViewDto();
        notificationRequest.setMaintenanceNum(MAINTENANCE_NUM)
                            .setTaskNum(TASK_NUM)
                            .setStatus(STATUS)
                            .setClassification(CLASSIFICATION);
    }


    @Test
    void notificationExists(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.updateNotification(NOTIFICATION_NUM, notificationRequest);
                });
    }

    @Test
    void updateNotificationTest(){
        Mockito.when(mockedNotificationValidationService.notificationExists(NOTIFICATION_NUM)).thenReturn(true);

        Mockito.when(mockedNotificationRepository.findByNotificationNum(NOTIFICATION_NUM)).thenReturn(notificationToBeSaved);

        Mockito.when(mockedModelMapper.map(notificationRequest, NotificationEntity.class)).thenReturn(notificationToBeSaved);
        Mockito.when(mockedNotificationRepository.save(notificationToBeSaved)).thenReturn(notificationToBeSaved);
        Mockito.when(mockedModelMapper.map(notificationToBeSaved, NotificationViewDto.class)).thenReturn(notificationRequest);

        NotificationViewDto notificationUpdated = testService.updateNotification(NOTIFICATION_NUM, notificationRequest);

        Assertions.assertEquals(NOTIFICATION_NUM, notificationUpdated.getNotificationNum());
    }

    @Test
    void createNotificationTest(){

    }

}
