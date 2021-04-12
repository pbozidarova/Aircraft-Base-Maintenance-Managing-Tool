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
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class AircraftValidationServiceImplTest {

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


}
