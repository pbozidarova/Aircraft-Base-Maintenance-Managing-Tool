package managing.tool.e_user.service.impl;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.exception.NotFoundInDb;
import managing.tool.util.JwtUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    private UserServiceImpl testService;
    private UserEntity existingUser;
    private UserEntity existingUser2;
    private UserViewDto userView;

    @Mock
    private Random mockedRandom;
    @Mock
    private UserRepository mockedUserRepository;
    @Mock
    private ModelMapper mockedModelMapper;
    @Mock
    private JwtUtil mockedJwtUtil;

    @BeforeEach
    public void setUp(){
        testService = new UserServiceImpl(mockedModelMapper, mockedUserRepository, mockedRandom, mockedJwtUtil);

        this.existingUser = new UserEntity();
        FacilityEntity facilityEntity = new FacilityEntity();
        facilityEntity.setName("Facility");
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity role = new RoleEntity();
        RoleEntity authority = new RoleEntity();
        role.setName(RoleEnum.valueOf("ADMIN"));
        authority.setName(RoleEnum.valueOf("ENGINEER"));

        roles.add(role);
        roles.add(authority);

        existingUser.setCompanyNum("N20202")
                .setPassword("202")
                .setRoles(roles)
                .setFacility(facilityEntity)
                .setEmail("p@email.com")
                .setFirstName("Petya")
                .setId(1L);

        this.userView = new UserViewDto();
        userView.setCompanyNum("N20202")
                .setFirstName("Petya")
                .setFacility(existingUser.getFacility().getName())
                .setRoles(roles.toString());

        userView.setRoles( userView
                .getRoles()
                .replace("[", "")
                .replace("]", "")
        );

        this.existingUser2 = new UserEntity();
        existingUser2.setCompanyNum("N20203")
                .setPassword("202")
                .setRoles(roles)
                .setEmail("p@email.com")
                .setFacility(facilityEntity)
                .setFirstName("Aleks");

    }

    @Test
    void findUserTest(){

        Mockito.when(mockedUserRepository.findByCompanyNum("N20202"))
                .thenReturn(existingUser);

        Mockito.when(mockedModelMapper.map(existingUser, UserViewDto.class))
                .thenReturn(userView);

        Assertions.assertEquals(testService.findUser("N20202").getFirstName(), "Petya");
        Assertions.assertTrue(testService.findUser("N20202").getRoles().contains("ADMIN"));
        Assertions.assertTrue(testService.findUser("N20202").getRoles().contains("ENGINEER"));

    }

    @Test
    void findUserDetailsTest(){
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        Mockito.when(mockedUserRepository.findByCompanyNum("N20202"))
                .thenReturn(existingUser);

        Mockito.when(mockedModelMapper.map(existingUser, UserDetailsDto.class))
                .thenReturn(userDetailsDto);

        String rolesString = testService.findUserDetails("N20202").get().getRoles().toString();

        Assertions.assertEquals(testService.findUserDetails("N20202"), Optional.of(userDetailsDto));
        Assertions.assertTrue(rolesString.contains("ADMIN"));
        Assertions.assertTrue(rolesString.contains("ENGINEER"));
    }

    @Test
    void findAllUsersTest(){
        Mockito.when(mockedUserRepository.findAll())
                .thenReturn(List.of(existingUser, existingUser2));
        Mockito.when(mockedModelMapper.map(existingUser, UserViewDto.class))
                .thenReturn(userView);
        Mockito.when(mockedModelMapper.map(existingUser2, UserViewDto.class))
                .thenReturn(userView);

        Assertions.assertEquals(2, testService.findAllUsers().size());
    }

    @Test
    void findByCompanyNumTest(){
        Mockito.when(mockedUserRepository.findByCompanyNum("N20202"))
                .thenReturn(existingUser);

        Assertions.assertEquals(testService.findByCompanyNum("N20202").getFirstName(), "Petya");
    }

    @Test
    void userExistsTest(){
        Mockito.when(mockedUserRepository.findByCompanyNum("N20202"))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.userExists("N20202"));
    }

    @Test
    void emailExistsTest(){
        Mockito.when(mockedUserRepository.findByEmail("p@email.com"))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.emailExists("p@email.com"));
    }

    @Test
    void emailExistsForAnotherUserTest(){
        Mockito.when(mockedUserRepository.findByEmail("p@email.com"))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.emailExistsForAnotherUser("p@email.com", "N20203"));
    }


    @Test
    void getRandomUserTest(){
        Mockito.when(mockedUserRepository.count())
                .thenReturn((long)1);
        Mockito.when(mockedRandom.nextInt(1))
                .thenReturn(1);
        Mockito.when(mockedUserRepository.getOne(2L))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.getRandomUser().getCompanyNum().equals("N20202"));

    }

    @Test
    void findAllUserEntitiesTest(){
        Mockito.when(mockedUserRepository.findAll())
                .thenReturn(List.of(existingUser, existingUser2));

        Assertions.assertEquals(2, testService.findAll().size());
    }

//    @Test
//    void saveUserTest(){
//        Mockito.when(mockedUserRepository.saveAndFlush(existingUser))
//                .thenReturn(existingUser);

    //    testService.saveUser(existingUser);

//        Assertions.assertEquals(1, mockedUserRepository.count());
  //  }


}
