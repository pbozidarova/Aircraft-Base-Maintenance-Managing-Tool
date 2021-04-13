package managing.tool.e_facility.service.impl;

import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.service.impl.AircraftValidationServiceImpl;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class FacilityValidationServiceImplTest {

    private FacilityValidationServiceImpl testService;

    @Mock
    FacilityService mockedFacilityService;

    @BeforeEach
    void setUp(){
        testService = new FacilityValidationServiceImpl(mockedFacilityService);
    }


    @Test
    void validateIfFacilityExistsTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfFacilityExists("throw_error_aircraft");
                });
    }


}
