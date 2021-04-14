package managing.tool.e_maintenance.service.impl;

import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.service.AircraftValidationService;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.service.FacilityValidationService;
import managing.tool.e_maintenance.repository.MaintenanceRepository;
import managing.tool.e_task.service.TaskSeedService;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class MaintenanceServiceImplTest {
    MaintenanceServiceImpl testService;

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
    }

    @Test
    void findAllMaintenanceEventsTest(){

    }

}
