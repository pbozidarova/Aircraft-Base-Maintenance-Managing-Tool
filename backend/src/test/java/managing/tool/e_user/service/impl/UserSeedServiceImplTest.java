package managing.tool.e_user.service.impl;

import com.google.gson.Gson;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserSeedServiceImplTest {
    private UserSeedServiceImpl testService;
    private Gson gson;

    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    UserRepository mockedUserRepository;
    @Mock
    RoleService mockedRoleService;
    @Mock
    PasswordEncoder mockedPasswordEncoder;

    @BeforeEach
    void setUp(){
        testService = new UserSeedServiceImpl(gson, mockedModelMapper, mockedUserRepository, mockedRoleService, mockedPasswordEncoder);
    }

    @Test
    void userAreImported(){
        Mockito.when(mockedUserRepository.count()).thenReturn(1L);

        Assertions.assertTrue(testService.userAreImported());
    }
}
