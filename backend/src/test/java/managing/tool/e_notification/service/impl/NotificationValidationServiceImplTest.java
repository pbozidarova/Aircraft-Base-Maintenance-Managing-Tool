package managing.tool.e_notification.service.impl;

import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.service.NotificationValidationService;
import managing.tool.e_task.service.TaskService;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NotificationValidationServiceImplTest {
    private final String INVALID_VALUE = "Invalid value";

    private NotificationValidationServiceImpl testService;

    @Mock
    MaintenanceService mockedMaintenanceService;
    @Mock
    TaskService mockedTaskService;

    @BeforeEach
    void setUp(){
        testService = new NotificationValidationServiceImpl(mockedMaintenanceService, mockedTaskService);

    }

    @Test
    void validateStatusTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateStatus(INVALID_VALUE);
                });

    }

    @Test
    void validateClassificationTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateClassification(INVALID_VALUE);
                });

    }

    @Test
    void validateIfMaintenanceExistsTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfMaintenanceExists(INVALID_VALUE);
                });

    }
    @Test
    void validateIfTaskExistsTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfTaskExists(INVALID_VALUE);
                });

    }

}
