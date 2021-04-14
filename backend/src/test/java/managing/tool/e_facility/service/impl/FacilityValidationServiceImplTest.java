package managing.tool.e_facility.service.impl;

import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.service.impl.AircraftValidationServiceImpl;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class FacilityValidationServiceImplTest {
    private final String FACILITY_NAME = "Facility name";

    private FacilityValidationServiceImpl testService;
    private FacilityEntity facilityEntity;

    @Mock
    FacilityService mockedFacilityService;

    @BeforeEach
    void setUp(){
        testService = new FacilityValidationServiceImpl(mockedFacilityService);

        facilityEntity = new FacilityEntity();
    }


    @Test
    void validateIfFacilityExistsTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfFacilityExists(FACILITY_NAME);
                });
    }

    @Test
    void validateFacilityPass(){
        Mockito.when(mockedFacilityService.facilityExists(FACILITY_NAME)).thenReturn(true);

        testService.validateIfFacilityExists(FACILITY_NAME);

        Mockito.verify(mockedFacilityService).facilityExists(FACILITY_NAME);
    }


}
