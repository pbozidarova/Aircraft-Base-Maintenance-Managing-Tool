package managing.tool.e_maintenance.service.impl;

import com.google.gson.Gson;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.service.impl.FacilitySeedServiceImpl;
import managing.tool.e_maintenance.repository.MaintenanceRepository;
import managing.tool.e_task.service.TaskSeedService;
import managing.tool.e_user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class MaintenanceSeedServiceImplTest {
    private MaintenanceSeedServiceImpl testService;
    private Gson gson;

    @Mock
    MaintenanceRepository mockedMaintenanceRepository;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    AircraftService mockedAircraftService;
    @Mock
    FacilityService mockedFacilityService;
    @Mock
    UserService mockedUserService;
    @Mock
    TaskSeedService mockedTaskService;

    @BeforeEach
    void setUp() {
        testService = new MaintenanceSeedServiceImpl(mockedMaintenanceRepository,
                mockedModelMapper, gson, mockedAircraftService, mockedFacilityService, mockedUserService, mockedTaskService);
    }

    @Test
    void eventsAreImported(){
        Mockito.when(mockedMaintenanceRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.areEventsImported());
    }

}
