package managing.tool.e_aircraft.service.impl;

import com.google.gson.Gson;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.e_facility.model.FacilityEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class AircraftSeedServiceImplTest {
    private AircraftSeedServiceImpl testService;
    private Gson gson;

    @Mock
    AircraftRepository mockedAircraftRepository;
    @Mock
    ModelMapper mockedModelMapper;

    @BeforeEach
    void setUp(){
        testService = new AircraftSeedServiceImpl(mockedAircraftRepository, gson, mockedModelMapper);
    }

    @Test
    void tasksAreImported(){
        Mockito.when(mockedAircraftRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.aircraftAreImported());
    }


}
