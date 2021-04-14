package managing.tool.e_aircraft.service.impl;

import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.impl.UserValidationServiceImpl;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AircraftValidationServiceImplTest {
    private final String VALID_VALUE = "Valid value";

    private AircraftValidationServiceImpl testService;

    @Mock
    AircraftService mockedAircraftService;

    @BeforeEach
    void setUp(){
        testService = new AircraftValidationServiceImpl(mockedAircraftService);
    }


    @Test
    void validateIfAircraftExistsTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfAircraftExists("throw_error_aircraft");
                });
    }

    @Test
    void validateIfAircraftExistsTestPass(){
        Mockito.when(mockedAircraftService.aircraftExists(VALID_VALUE)).thenReturn(true);

        testService.validateIfAircraftExists(VALID_VALUE);

        Mockito.verify(mockedAircraftService).aircraftExists(VALID_VALUE);
    }



}
