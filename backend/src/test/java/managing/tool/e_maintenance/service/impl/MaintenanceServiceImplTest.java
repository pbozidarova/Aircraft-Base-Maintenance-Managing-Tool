package managing.tool.e_maintenance.service.impl;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.service.AircraftValidationService;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.service.FacilityValidationService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.dto.MaintenanceRequestDto;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.repository.MaintenanceRepository;
import managing.tool.e_notification.model.dto.NotificationViewDto;
import managing.tool.e_task.service.TaskSeedService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceImplTest {
    private final String MAINTENANCE_NUM = "M123";
    private final String FACILITY_NAME = "Facility name";
    private final String AIRCRAFT_REGISTRATION = "LZ-REG";
    private final String STATUS_CLOSED = "CLOSED";
    private final String STATUS_OPENED = "OPENED";
    private final String JWT_STRING = "jwt";
    private final String AUTHOR_STRING = "N123 - LN, FN";

    MaintenanceServiceImpl testService;
    MaintenanceEntity maintenanceEntity;
    MaintenanceEntity maintenanceToBeSaved;
    MaintenanceViewDto maintenanceResponse;
    MaintenanceRequestDto maintenanceRequest;

    FacilityEntity facilityEntity;
    UserEntity responsibleEngineer;
    AircraftEntity aircraftEntity;

    @Mock
    MaintenanceRepository mockedMaintenanceRepository;
    @Mock
    TaskSeedService mockedTaskService;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    UserService mockedUserService;
    @Mock
    AircraftService mockedAircraftService;
    @Mock
    FacilityService mockedFacilityService;
    @Mock
    FacilityValidationService mockedFacilityValidationService;
    @Mock
    UserValidationService mockedUserValidationService;
    @Mock
    AircraftValidationService mockedAircraftValidationService;
    @Mock
    Random mockedRandom;

    @BeforeEach
    void setUp(){
        testService = new MaintenanceServiceImpl(mockedMaintenanceRepository,
                mockedTaskService,
                mockedModelMapper,
                mockedUserService,
                mockedAircraftService,
                mockedFacilityService,
                mockedFacilityValidationService,
                mockedUserValidationService,
                mockedAircraftValidationService,
                mockedRandom
        );

        facilityEntity = new FacilityEntity();
        facilityEntity.setName(FACILITY_NAME);
        aircraftEntity = new AircraftEntity();
        aircraftEntity.setAircraftRegistration(AIRCRAFT_REGISTRATION);

        responsibleEngineer = new UserEntity();
        responsibleEngineer.setCompanyNum(AUTHOR_STRING)
                .setFirstName(AUTHOR_STRING)
                .setLastName(AUTHOR_STRING);

        maintenanceEntity = new MaintenanceEntity().setMaintenanceNum(MAINTENANCE_NUM)
                .setFacility(facilityEntity)
                .setAircraft(aircraftEntity)
                .setResponsibleEngineer(responsibleEngineer)
                .setStartDate(LocalDate.now())
                .setEndDate(LocalDate.now());

        maintenanceToBeSaved = new MaintenanceEntity();
        maintenanceToBeSaved.setMaintenanceNum(MAINTENANCE_NUM)
                .setFacility(facilityEntity)
                .setAircraft(aircraftEntity)
                .setResponsibleEngineer(responsibleEngineer);

        maintenanceResponse = new MaintenanceViewDto();
        maintenanceResponse.setMaintenanceNum(MAINTENANCE_NUM)
                        .setAircraftRegistration(AIRCRAFT_REGISTRATION)
                        .setStatus(STATUS_CLOSED);

        maintenanceRequest = new MaintenanceRequestDto();
        maintenanceRequest.setMaintenanceNum(MAINTENANCE_NUM)
                .setAircraftRegistration(AIRCRAFT_REGISTRATION)
                .setResponsibleEngineer(AUTHOR_STRING)
                .setStartDate(LocalDate.now().minusDays(2))
                .setEndDate(LocalDate.now().minusDays(1));
    }

    @Test
    void findAllMaintenanceEventsTest(){
        Mockito.when(mockedMaintenanceRepository.findAll()).thenReturn(List.of(maintenanceToBeSaved));
        Mockito.when(mockedModelMapper.map(maintenanceToBeSaved, MaintenanceViewDto.class)).thenReturn(maintenanceResponse);
        Mockito.when(mockedUserService.userViewStringBuild(responsibleEngineer)).thenReturn(AUTHOR_STRING);

        Assertions.assertEquals(testService.findAllMaintenanceEvents().size(), 1 );

    }

    @Test
    void findMaintenanceByNumTest(){
        Mockito.when(mockedMaintenanceRepository.findByMaintenanceNum(MAINTENANCE_NUM)).thenReturn(maintenanceEntity);

        Assertions.assertEquals(testService.findByMaintenanceNum(MAINTENANCE_NUM).getAircraft().getAircraftRegistration(),
                maintenanceEntity.getAircraft().getAircraftRegistration());

    }

    @Test
    void findMaintenanceByNumViewTest(){
        Mockito.when(mockedMaintenanceRepository.findByMaintenanceNum(MAINTENANCE_NUM))
                .thenReturn(maintenanceEntity);
//        Mockito.when(mockedModelMapper.map(maintenanceEntity, MaintenanceViewDto.class))
//                .thenReturn(maintenanceRequest);

        Assertions.assertEquals(testService.findByMaintenanceNum(MAINTENANCE_NUM).getAircraft().getAircraftRegistration(),
                maintenanceResponse.getAircraftRegistration());

    }
    @Test
    void maintenanceExistsTest(){
        Mockito.when(mockedMaintenanceRepository.findByMaintenanceNum(MAINTENANCE_NUM)).thenReturn(maintenanceEntity);

        Assertions.assertTrue(testService.maintenanceExists(MAINTENANCE_NUM));
    }

    @Test
    void findAllMaintenanceByResponsibleEngineerTest(){
        Mockito.when(mockedMaintenanceRepository.findAllByResponsibleEngineer(responsibleEngineer))
                .thenReturn(List.of(maintenanceEntity));
        Mockito.when(mockedUserService.findByCompanyNum(AUTHOR_STRING))
                .thenReturn(responsibleEngineer);
        Mockito.when(mockedModelMapper.map(maintenanceEntity, MaintenanceViewDto.class))
                .thenReturn(maintenanceResponse);

        Assertions.assertEquals(testService.findAllMaintenanceByResponsibleEngineer(AUTHOR_STRING).size(), 1);
    }

    @Test
    void findAllByAircraftTest(){
        Mockito.when(mockedMaintenanceRepository.findAllByAircraft_AircraftRegistration(AIRCRAFT_REGISTRATION))
                .thenReturn(List.of(maintenanceEntity));

        Mockito.when(mockedModelMapper.map(maintenanceEntity, MaintenanceViewDto.class))
                .thenReturn(maintenanceResponse);

        Assertions.assertEquals(testService.findAllByAircraft(AIRCRAFT_REGISTRATION).size(), 1);
    }

    @Test
    void getRandomMaintenance(){

        Mockito.when(mockedMaintenanceRepository.count())
                .thenReturn((long)1);
        Mockito.when(mockedRandom.nextInt(1))
                .thenReturn(1);
        Mockito.when(mockedMaintenanceRepository.getOne(2L))
                .thenReturn(maintenanceEntity);

        Assertions.assertTrue(testService.getRandomMaintenance().getMaintenanceNum().equals(MAINTENANCE_NUM));
    }

    @Test
    void findAllMaintenanceByFacilityTest(){
        Mockito.when(mockedMaintenanceRepository.findAllByFacility(facilityEntity))
                .thenReturn(List.of(maintenanceEntity));
        Mockito.when(mockedFacilityService.getFacilityByName(FACILITY_NAME))
                .thenReturn(facilityEntity);
        Mockito.when(mockedModelMapper.map(maintenanceEntity, MaintenanceViewDto.class))
                .thenReturn(maintenanceResponse);

        Assertions.assertEquals(testService.findAllByFacility(FACILITY_NAME).size(), 1);
    }

    @Test
    void noSuchMaintenance(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.updateMaintenance(MAINTENANCE_NUM, maintenanceRequest, JWT_STRING);
                });
    }

    @Test
    void updateMaintenanceTest(){
        Mockito.when(mockedMaintenanceRepository.findByMaintenanceNum(MAINTENANCE_NUM)).thenReturn(maintenanceToBeSaved);

        Mockito.when(mockedModelMapper.map(maintenanceRequest, MaintenanceEntity.class)).thenReturn(maintenanceToBeSaved);
        Mockito.when(mockedMaintenanceRepository.save(maintenanceToBeSaved)).thenReturn(maintenanceToBeSaved);
        Mockito.when(mockedModelMapper.map(maintenanceToBeSaved, MaintenanceViewDto.class)).thenReturn(maintenanceResponse);

        MaintenanceViewDto maintenanceUpdated = testService.updateMaintenance(MAINTENANCE_NUM, maintenanceRequest, JWT_STRING);

        Assertions.assertEquals(MAINTENANCE_NUM, maintenanceUpdated.getMaintenanceNum());
        Assertions.assertTrue(maintenanceUpdated.getStatus().equals(STATUS_CLOSED));
    }

    @Test
    void maintenanceExistsWhenCreatingTest(){
        Mockito.when(mockedMaintenanceRepository.findByMaintenanceNum(MAINTENANCE_NUM)).thenReturn(maintenanceEntity);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.createMaintenance(MAINTENANCE_NUM, maintenanceRequest, JWT_STRING);
                });
    }


    @Test
    void createMaintenanceTest(){

        Mockito.when(mockedModelMapper.map(maintenanceRequest, MaintenanceEntity.class)).thenReturn(maintenanceToBeSaved);
        Mockito.when(mockedMaintenanceRepository.save(maintenanceToBeSaved)).thenReturn(maintenanceToBeSaved);
        Mockito.when(mockedModelMapper.map(maintenanceToBeSaved, MaintenanceViewDto.class)).thenReturn(maintenanceResponse);

        MaintenanceViewDto maintenanceCreated = testService.createMaintenance(MAINTENANCE_NUM, maintenanceRequest, JWT_STRING);

        Assertions.assertEquals(MAINTENANCE_NUM, maintenanceCreated.getMaintenanceNum());
        Assertions.assertTrue(maintenanceCreated.getStatus().equals(STATUS_CLOSED));


    }
}
