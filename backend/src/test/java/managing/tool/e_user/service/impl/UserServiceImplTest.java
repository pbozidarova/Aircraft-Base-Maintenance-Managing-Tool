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

import static managing.tool.e_user.service.impl.UserMockValues.*;

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
        facilityEntity.setName(FACILITY_NAME);
        Set<RoleEntity> roles = new HashSet<>();
        RoleEntity role = new RoleEntity();
        RoleEntity authority = new RoleEntity();
        role.setName(RoleEnum.valueOf(ADMIN_STRING));
        authority.setName(RoleEnum.valueOf(ENGINEER_STRING));

        roles.add(role);
        roles.add(authority);

        existingUser.setCompanyNum(VALID_COMPANY_NUM)
                .setPassword(VALID_PASSWORD)
                .setRoles(roles)
                .setFacility(facilityEntity)
                .setEmail(VALID_EMAIL)
                .setFirstName(VALID_USER_FIRST_NAME)
                .setLastName(VALID_USER_LAST_NAME)
                .setId(1L);

        this.userView = new UserViewDto();
        userView.setCompanyNum(VALID_COMPANY_NUM)
                .setFirstName(VALID_USER_FIRST_NAME)
                .setFacility(existingUser.getFacility().getName())
                .setRoles(roles.toString());

        userView.setRoles( userView
                .getRoles()
                .replace("[", "")
                .replace("]", "")
        );

        this.existingUser2 = new UserEntity();
        existingUser2.setCompanyNum(VALID_COMPANY_NUM2)
                .setPassword(VALID_PASSWORD)
                .setRoles(roles)
                .setEmail(VALID_EMAIL)
                .setFacility(facilityEntity)
                .setFirstName(VALID_USER_FIRST_NAME2);

    }

    @Test
    void findUserTest(){

        Mockito.when(mockedUserRepository.findByCompanyNum(VALID_COMPANY_NUM))
                .thenReturn(existingUser);

        Mockito.when(mockedModelMapper.map(existingUser, UserViewDto.class))
                .thenReturn(userView);

        Assertions.assertEquals(testService.findUser(VALID_COMPANY_NUM).getFirstName(), VALID_USER_FIRST_NAME);
        Assertions.assertTrue(testService.findUser(VALID_COMPANY_NUM).getRoles().contains(ADMIN_STRING));
        Assertions.assertTrue(testService.findUser(VALID_COMPANY_NUM).getRoles().contains(ENGINEER_STRING));

    }

    @Test
    void findUserDetailsTest(){
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        Mockito.when(mockedUserRepository.findByCompanyNum(VALID_COMPANY_NUM2))
                .thenReturn(existingUser);

        Mockito.when(mockedModelMapper.map(existingUser, UserDetailsDto.class))
                .thenReturn(userDetailsDto);

        String rolesString = testService.findUserDetails(VALID_COMPANY_NUM2).get().getRoles().toString();

        Assertions.assertEquals(testService.findUserDetails(VALID_COMPANY_NUM2), Optional.of(userDetailsDto));
        Assertions.assertTrue(rolesString.contains(ADMIN_STRING));
        Assertions.assertTrue(rolesString.contains(ENGINEER_STRING));
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
        Mockito.when(mockedUserRepository.findByCompanyNum(VALID_COMPANY_NUM))
                .thenReturn(existingUser);

        Assertions.assertEquals(testService.findByCompanyNum(VALID_COMPANY_NUM).getFirstName(), VALID_USER_FIRST_NAME);
    }

    @Test
    void userExistsTest(){
        Mockito.when(mockedUserRepository.findByCompanyNum(VALID_COMPANY_NUM))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.userExists(VALID_COMPANY_NUM));
    }

    @Test
    void emailExistsTest(){
        Mockito.when(mockedUserRepository.findByEmail(VALID_EMAIL))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.emailExists(VALID_EMAIL));
    }

    @Test
    void emailExistsForAnotherUserTest(){
        Mockito.when(mockedUserRepository.findByEmail(VALID_EMAIL))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.emailExistsForAnotherUser(VALID_EMAIL, VALID_COMPANY_NUM2));
    }


    @Test
    void getRandomUserTest(){
        Mockito.when(mockedUserRepository.count())
                .thenReturn((long)1);
        Mockito.when(mockedRandom.nextInt(1))
                .thenReturn(1);
        Mockito.when(mockedUserRepository.getOne(2L))
                .thenReturn(existingUser);

        Assertions.assertTrue(testService.getRandomUser().getCompanyNum().equals(VALID_COMPANY_NUM));

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

    @Test
    void userSaveTest(){
//        Mockito.when(mockedUserRepository.save(existingUser)).thenReturn(existingUser);

        testService.saveUser(existingUser);
        System.out.println(mockedUserRepository.count());
        Assertions.assertTrue(mockedUserRepository.count() == 1);
    }

    @Test
    void identifyingUserFromTokenTest(){
        Mockito.when(mockedJwtUtil.extractUsername(JWT_STRING)).thenReturn(VALID_COMPANY_NUM);

        Mockito.when(mockedUserRepository.findByCompanyNum(VALID_COMPANY_NUM)).thenReturn(existingUser);

        Assertions.assertTrue(testService
                .identifyingUserFromToken(JWT_STRING)
                .getCompanyNum()
                .equals(VALID_COMPANY_NUM));
    }

    @Test
    void userViewStringBuildTest(){
        Assertions.assertTrue(testService.userViewStringBuild(existingUser)
                                .equals(String.format("%s - %s, %s"
                                        ,existingUser.getCompanyNum(),
                                        existingUser.getLastName(),
                                        existingUser.getFirstName())));
    }

    @Test
    void companyNumFromUserStringTest(){
        Assertions.assertTrue(testService
                .companyNumFromUserString(VALID_COMPANY_NUM + " - ")
                .equals(VALID_COMPANY_NUM));
    }

}
