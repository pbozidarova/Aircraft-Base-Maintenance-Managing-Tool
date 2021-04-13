package managing.tool.e_task.service.impl;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Random;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    private final String VALID_TASK_NUM = "1234";
    private final String INVALID_TASK_NUM = "Invalid_Num";

    private TaskServiceImpl testService;
    private TaskViewDto taskRequestData;
    private TaskEntity taskExisting;
    private TaskEntity taskToBeSaved;
    private UserEntity userEntity;
    private MaintenanceEntity maintenanceEntity;

    @Mock
    TaskRepository mockedTaskRepository;
    @Mock
    UserService mockedUserService;
    @Mock
    MaintenanceService mockedMaintenanceService;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    Random mockedRandom;

    @BeforeEach
    void setUp(){
        testService = new TaskServiceImpl(mockedTaskRepository, mockedUserService, mockedMaintenanceService,mockedModelMapper, mockedRandom);

        maintenanceEntity = new MaintenanceEntity();
        userEntity = new UserEntity();
        taskExisting = new TaskEntity();
        taskExisting.setPreparedBy(Set.of(userEntity))
                    .setTaskNum(VALID_TASK_NUM)
                    .setMaintenances(Set.of(maintenanceEntity));
        taskRequestData = new TaskViewDto();
    }

    @Test
    void findAllTasksTest(){
        Mockito.when(mockedTaskRepository.findAll()).thenReturn(List.of(taskExisting));
        Mockito.when(mockedModelMapper.map(taskExisting, TaskViewDto.class)).thenReturn(taskRequestData);

        Assertions.assertEquals(1, testService.findAllTasks().size());
    }

    @Test
    void findAllMaintenanceByTaskTest(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskExisting);

        Assertions.assertEquals(1, testService.findAllMaintenanceByTask(VALID_TASK_NUM).size());
    }

}
