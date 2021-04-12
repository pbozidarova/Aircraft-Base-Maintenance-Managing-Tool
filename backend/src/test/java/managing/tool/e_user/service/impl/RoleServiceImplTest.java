package managing.tool.e_user.service.impl;


import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    private RoleServiceImpl testService;

    @Mock
    RoleRepository mockedRoleRepository;

    @BeforeEach
    public void setUp(){
        testService = new RoleServiceImpl(mockedRoleRepository);
    }

    @Test
    public void findByNameTest(){
        RoleEnum roleEnum = RoleEnum.valueOf("ADMIN");
        RoleEntity role = new RoleEntity();
        role.setName(roleEnum);

        Mockito.when(mockedRoleRepository.findByName(roleEnum))
                .thenReturn(role);

        RoleEntity roleEntityFound = testService.findByName(roleEnum);

        Assertions.assertEquals(role, roleEntityFound);
    }

    @Test
    public void roleExistsTest(){
        RoleEnum roleEnum = RoleEnum.valueOf("ADMIN");
        RoleEntity role = new RoleEntity();
        role.setName(roleEnum);

        Mockito.when(mockedRoleRepository.findByName(roleEnum))
                .thenReturn(role);

        Assertions.assertTrue(this.testService.roleExists(roleEnum));
    }
}
