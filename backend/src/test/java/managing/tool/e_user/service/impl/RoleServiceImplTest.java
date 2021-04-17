package managing.tool.e_user.service.impl;


import managing.tool.e_notification.model.ReplyEntity;
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

import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    private RoleServiceImpl testService;

    @Mock
    RoleRepository mockedRoleRepository;
    @Mock
    Random mokedRandom;

    @BeforeEach
    public void setUp() {
        testService = new RoleServiceImpl(mockedRoleRepository, mokedRandom);
    }


    @Test
    public void findByNameTest(){
        String roleEnum = "ADMIN";
        RoleEntity role = new RoleEntity();
        role.setName(RoleEnum.valueOf(roleEnum));

        Mockito.when(mockedRoleRepository.findByName(RoleEnum.valueOf(roleEnum)))
                .thenReturn(role);

        RoleEntity roleEntityFound = testService.findByName(roleEnum);

        Assertions.assertEquals(role, roleEntityFound);
    }

    @Test
    void roleExistsTest(){
        String roleExisting = "ADMIN";
        String roleNotExisting = "MADE_UP";

        Assertions.assertTrue(this.testService.roleExists(roleExisting));
        Assertions.assertFalse(this.testService.roleExists(roleNotExisting));
    }


}
