package managing.tool.e_notification.service.impl;

import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.repository.NotificationRepository;
import managing.tool.e_notification.service.CloudinaryService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {
    private NotificationServiceImpl testService;

    @Mock
    NotificationRepository notificationRepository;
    @Mock
    UserService userService;
    @Mock
    TaskService taskService;
    @Mock
    MaintenanceService maintenanceService;
    @Mock
    ModelMapper modelMapper;
    @Mock
    ReplyService replyService;
    @Mock
    Random random;
    @Mock
    CloudinaryService cloudinaryService;
}
