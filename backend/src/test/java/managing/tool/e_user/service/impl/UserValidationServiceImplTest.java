package managing.tool.e_user.service.impl;

import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserValidationServiceImplTest {
    private final String INVALID_VALUE = "Invalid value";
    private final String VALID_VALUE = "Valid value";

    private UserValidationServiceImpl testService;

    @Mock
    UserService mockedUserService;
    @Mock
    RoleService mockedRoleService;

    @BeforeEach
    void setUp(){
        testService = new UserValidationServiceImpl(mockedUserService, mockedRoleService);
    }

    @Test
    void validateIfUserExistTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfUserExists("throw_error_user");
                });
    }

    @Test
    void validateIfUserExistTestPass(){
        Mockito.when(mockedUserService.userExists(VALID_VALUE)).thenReturn(true);

        testService.validateIfUserExists(VALID_VALUE);

        Mockito.verify(mockedUserService).userExists(VALID_VALUE);
    }


    @Test
    void validateIfRolesExistTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfRolesExist("throw, error_role");
                });
    }

    @Test
    void validateIfRolesExistPass(){
        Mockito.when(mockedRoleService.roleExists(VALID_VALUE)).thenReturn(true);
        Mockito.when(mockedRoleService.roleExists(INVALID_VALUE)).thenReturn(true);

        testService.validateIfRolesExist(String.format("%s, %s", VALID_VALUE, INVALID_VALUE));

        Mockito.verify(mockedRoleService).roleExists(VALID_VALUE);
        Mockito.verify(mockedRoleService).roleExists(INVALID_VALUE);
    }


}
