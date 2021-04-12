package managing.tool.e_facility.service.impl;

import com.google.gson.Gson;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_task.repository.TaskRepository;
import managing.tool.e_task.service.impl.TaskSeedServiceImpl;
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
public class FacilitySeedServiceImplTest {
    private FacilitySeedServiceImpl testService;
    private Gson gson;

    @Mock
    FacilityRepository mockedFacilityRepository;
    @Mock
    UserService mockedUserService;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    Random mockedRandom;

    @BeforeEach
    void setUp(){
        testService = new FacilitySeedServiceImpl(mockedFacilityRepository, mockedUserService, mockedModelMapper, mockedRandom, gson);
    }

    @Test
    void tasksAreImported(){
        Mockito.when(mockedFacilityRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.areFacilitiesImported());
    }

    @Test
    void getRandomFacilityTest(){
        FacilityEntity facility = new FacilityEntity();
        facility.setName("Test Facility Name");

        Mockito.when(mockedFacilityRepository.count())
                .thenReturn((long)1);
        Mockito.when(mockedRandom.nextInt(1))
                .thenReturn(1);
        Mockito.when(mockedFacilityRepository.getOne(2L))
                .thenReturn(facility);

        Assertions.assertTrue(testService.getRandomFacility().getName().equals("Test Facility Name"));

    }
}
