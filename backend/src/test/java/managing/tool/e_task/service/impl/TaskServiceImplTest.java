package managing.tool.e_task.service.impl;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.model.dto.TaskRequestDto;
import managing.tool.e_task.model.dto.TaskViewDto;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserViewDto;
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

import java.util.List;
import java.util.Random;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {
    private final String VALID_TASK_NUM = "1234";
    private final String INVALID_TASK_NUM = "Invalid_Num";
    private final String VALID_REG = "LZ_Num";
    private final String VALID_FACILITY_NAME = "Valid name";
    private final String VALID_USER_STRING = "1234 - Name1 Name2";
    private final String VALID_COMPANY_NUM = "N12345";
    private final String VALID_MAINTENANCE_NUM = "N12345";
    private final String JWT_STRING = "jwt";
    private final String OLD_CODE = "FUC";
    private final String NEW_CODE = "GVI";

    private TaskServiceImpl testService;
    private TaskViewDto taskReturnData;
    private TaskRequestDto taskRequestUpdate;
    private TaskEntity taskExisting;
    private TaskEntity taskToBeSaved;
    private UserEntity userEntity;
    private UserViewDto userViewDto;
    private MaintenanceEntity maintenanceEntity;
    private MaintenanceViewDto maintenanceViewDto;
    private AircraftEntity aircraftEntity;
    private FacilityEntity facilityEntity;

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

        userEntity = new UserEntity();
        userEntity.setCompanyNum(VALID_COMPANY_NUM);
        facilityEntity = new FacilityEntity();
        aircraftEntity = new AircraftEntity();
        aircraftEntity.setAircraftRegistration(VALID_REG);

        maintenanceEntity = new MaintenanceEntity();
        maintenanceEntity.setResponsibleEngineer(userEntity)
                        .setFacility(facilityEntity)
                        .setAircraft(aircraftEntity)
                        .setMaintenanceNum(VALID_MAINTENANCE_NUM);
        maintenanceViewDto = new MaintenanceViewDto();

        taskExisting = new TaskEntity();
        taskExisting.setPreparedBy(Set.of(userEntity))
                    .setTaskNum(VALID_TASK_NUM)
                    .setCode(OLD_CODE)
                    .setMaintenances(Set.of(maintenanceEntity))
                    .setId(1L);

        taskToBeSaved = new TaskEntity();
        taskToBeSaved.setPreparedBy(Set.of(userEntity))
                .setTaskNum(VALID_TASK_NUM)
                .setCode(OLD_CODE)
                .setMaintenances(Set.of(maintenanceEntity))
                .setId(1L);


        taskReturnData = new TaskViewDto();
        taskReturnData.setTaskNum(VALID_TASK_NUM)
                        .setCode(NEW_CODE);

        taskRequestUpdate = new TaskRequestDto();
        taskRequestUpdate.setTaskNum(VALID_TASK_NUM);
    }

    @Test
    void findAllTasksTest(){
        Mockito.when(mockedTaskRepository.findAll()).thenReturn(List.of(taskExisting));
        Mockito.when(mockedModelMapper.map(taskExisting, TaskViewDto.class)).thenReturn(taskReturnData);

        Assertions.assertEquals(1, testService.findAllTasks().size());
    }

    @Test
    void findAllMaintenanceByTaskTest(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskExisting);
        Mockito.when(mockedModelMapper.map(maintenanceEntity, MaintenanceViewDto.class)).thenReturn(maintenanceViewDto);
        Mockito.when(mockedUserService.userViewStringBuild(userEntity)).thenReturn(VALID_USER_STRING);

        Assertions.assertEquals(1, testService.findAllMaintenanceByTask(VALID_TASK_NUM).size());
    }

    @Test
    void findAllByAuthorTest(){
        Mockito.when(mockedTaskRepository.findAllByPreparedByContains(userEntity)).thenReturn(List.of(taskExisting));
        Mockito.when(mockedUserService.findByCompanyNum(VALID_COMPANY_NUM)).thenReturn(userEntity);
        Mockito.when(mockedModelMapper.map(taskExisting, TaskViewDto.class)).thenReturn(taskReturnData);

        Assertions.assertEquals(1, testService.findAllByAuthor(VALID_COMPANY_NUM).size());
    }

    @Test
    void findAllAddedInMaintenance(){
        Mockito.when(mockedTaskRepository.findAllByMaintenancesContains(maintenanceEntity)).thenReturn(List.of(taskExisting));
        Mockito.when(mockedMaintenanceService.findByMaintenanceNum(VALID_MAINTENANCE_NUM)).thenReturn(maintenanceEntity);

        Mockito.when(mockedModelMapper.map(taskExisting, TaskViewDto.class)).thenReturn(taskReturnData);

        Assertions.assertEquals(1, testService.findAllAddedInMaintenance(VALID_COMPANY_NUM).size());

    }

    @Test
    void createCorrectStatusTest(){
        TaskEntity taskExisting1 = new TaskEntity();
        taskExisting1.setToolingAvailable(true).setQualityAssured(true).setJobcardsPrepared(false);
        taskExisting.setToolingAvailable(true).setQualityAssured(true).setJobcardsPrepared(true);

        Assertions.assertEquals("OK", testService.createCorrectStatus(taskExisting));
        Assertions.assertEquals("NOT_OK", testService.createCorrectStatus(taskExisting1));
    }

    @Test
    void findTaskByTaskNumberTest(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskExisting);

        Assertions.assertEquals(taskExisting, testService.findTaskByTaskNumber(VALID_TASK_NUM));
    }

    @Test
    void taskExistsTest(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskExisting);

        Assertions.assertTrue(testService.taskExists(VALID_TASK_NUM));
        Assertions.assertFalse(testService.taskExists(INVALID_TASK_NUM));
    }

    @Test
    void getRandomTaskTest(){

        Mockito.when(mockedTaskRepository.count())
                .thenReturn((long)1);
        Mockito.when(mockedRandom.nextInt(1))
                .thenReturn(1);
        Mockito.when(mockedTaskRepository.getOne(1L))
                .thenReturn(taskExisting);

        Assertions.assertTrue(testService.getRandomTask().getTaskNum().equals(VALID_TASK_NUM));

    }

    @Test
    void findTaskTest(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskExisting);
        Mockito.when(mockedModelMapper.map(taskExisting, TaskViewDto.class)).thenReturn(taskReturnData);

        Assertions.assertEquals(VALID_TASK_NUM, testService.findTask(VALID_TASK_NUM).getTaskNum());

    }

    @Test
    void noSuchTask(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.updateTask(INVALID_TASK_NUM, taskRequestUpdate, JWT_STRING);
                });
    }

    @Test
    void updateTaskTest(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskToBeSaved);
        Mockito.when(mockedUserService.identifyingUserFromToken(JWT_STRING)).thenReturn(userEntity);

        Mockito.when(mockedModelMapper.map(taskRequestUpdate, TaskEntity.class)).thenReturn(taskToBeSaved);
        Mockito.when(mockedTaskRepository.save(taskToBeSaved)).thenReturn(taskToBeSaved);
        Mockito.when(mockedModelMapper.map(taskToBeSaved, TaskViewDto.class)).thenReturn(taskReturnData);

        TaskViewDto taskUpdate = testService.updateTask(VALID_TASK_NUM, taskRequestUpdate, JWT_STRING);

        Assertions.assertEquals(taskUpdate.getTaskNum(), VALID_TASK_NUM);
        Assertions.assertTrue(!taskUpdate.getCode().equals(taskToBeSaved.getCode()));
    }

    @Test
    void facilityAlreadyExists(){
        Mockito.when(mockedTaskRepository.findByTaskNum(VALID_TASK_NUM)).thenReturn(taskExisting);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.createTask(VALID_TASK_NUM, taskRequestUpdate, JWT_STRING);
                });
    }

    @Test
    void createTaskTest(){
        Mockito.when(mockedUserService.identifyingUserFromToken(JWT_STRING)).thenReturn(userEntity);

        Mockito.when(mockedModelMapper.map(taskRequestUpdate, TaskEntity.class)).thenReturn(taskToBeSaved);
        Mockito.when(mockedTaskRepository.save(taskToBeSaved)).thenReturn(taskToBeSaved);
        Mockito.when(mockedModelMapper.map(taskToBeSaved, TaskViewDto.class)).thenReturn(taskReturnData);

        TaskViewDto taskCreate = testService.createTask(VALID_TASK_NUM, taskRequestUpdate, JWT_STRING);

        Assertions.assertEquals(taskToBeSaved.getTaskNum(), taskCreate.getTaskNum());
    }

}
