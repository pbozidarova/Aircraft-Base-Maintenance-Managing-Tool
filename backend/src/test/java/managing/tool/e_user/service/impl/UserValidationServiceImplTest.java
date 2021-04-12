package managing.tool.e_user.service.impl;

import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class UserValidationServiceImplTest {

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
    void validateIfRolesExistTest(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.validateIfRolesExist("throw, error_role");
                });
    }


}
