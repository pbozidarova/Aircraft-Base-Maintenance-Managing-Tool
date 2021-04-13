package managing.tool.e_user.service.impl;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.service.FacilityValidationService;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static managing.tool.e_user.service.impl.UserMockValues.*;
import static managing.tool.e_user.service.impl.UserMockValues.VALID_USER_FIRST_NAME2;

@ExtendWith(MockitoExtension.class)
public class UserCreateUpdateServiceImplTest {
    private UserCreateUpdateServiceImpl testService;
    private UserEntity existingUser;
    private UserEntity existingUser2;
    private UserEntity userToBeSaved;
    private UserViewDto userView;
    private FacilityEntity facilityEntity;

    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    UserRepository mockedUserRepository;
    @Mock
    UserService mockedUserService;
    @Mock
    FacilityService mockedFacilityService;
    @Mock
    FacilityValidationService mockedFacilityValidationService;
    @Mock
    UserValidationService mockedUserValidationService;
    @Mock
    RoleService mockedRoleService;
    @Mock
    PasswordEncoder mockedPasswordEncoder;

    @BeforeEach
    void setUp(){
        testService = new UserCreateUpdateServiceImpl(mockedModelMapper,
                mockedUserRepository,
                mockedUserService,
                mockedFacilityService,
                mockedFacilityValidationService,
                mockedUserValidationService,
                mockedRoleService,
                mockedPasswordEncoder);

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

        userToBeSaved = new UserEntity();
        userToBeSaved.setCompanyNum(VALID_COMPANY_NUM)
                .setPassword(VALID_PASSWORD)
                .setRoles(roles)
                .setFacility(facilityEntity)
                .setEmail(VALID_EMAIL)
                .setFirstName(VALID_USER_FIRST_NAME2)
                .setLastName(VALID_USER_LAST_NAME)
                .setId(1L);

        this.userView = new UserViewDto();
        userView.setCompanyNum(VALID_COMPANY_NUM)
                .setFirstName(VALID_USER_FIRST_NAME2)
                .setEmail(VALID_EMAIL)
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

        facilityEntity.setName(FACILITY_NAME);
    }

    @Test
    void noSuchUser(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.updateUser(VALID_COMPANY_NUM, userView);
                });
    }

    @Test
    void emailExist(){
        Mockito.when(mockedUserService.userExists(VALID_COMPANY_NUM))
            .thenReturn(true);
        Mockito.when(mockedUserService.emailExistsForAnotherUser(userView.getEmail(), VALID_COMPANY_NUM))
                .thenReturn(true);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.updateUser(VALID_COMPANY_NUM, userView);
                });
    }

    @Test
    void updateUserTest(){
        Mockito.when(mockedUserService.userExists(VALID_COMPANY_NUM))
                .thenReturn(true);
        Mockito.when(mockedUserService.emailExistsForAnotherUser(userView.getEmail(), VALID_COMPANY_NUM))
                .thenReturn(false);

        Mockito.when(mockedFacilityService.getFacilityByName(FACILITY_NAME)).thenReturn(facilityEntity);
        Mockito.when(mockedUserRepository.findByCompanyNum(VALID_COMPANY_NUM)).thenReturn(existingUser);

        Mockito.when(mockedModelMapper.map(userView, UserEntity.class)).thenReturn(userToBeSaved);
        Mockito.when(mockedUserRepository.save(userToBeSaved)).thenReturn(userToBeSaved);
        Mockito.when(mockedModelMapper.map(userToBeSaved, UserViewDto.class)).thenReturn(userView);

        UserViewDto userUpdated = testService.updateUser(VALID_COMPANY_NUM, userView);

        Assertions.assertEquals(userUpdated.getCompanyNum(), VALID_COMPANY_NUM );
        Assertions.assertTrue(!userToBeSaved.getFirstName().equals(existingUser));
    }

    @Test
    void userExistsTest(){
        Mockito.when(mockedUserService.userExists(VALID_COMPANY_NUM)).thenReturn(true);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.createUser(VALID_COMPANY_NUM, userView);
                });
    }

    @Test
    void emailExistTest(){
        Mockito.when(mockedUserService.userExists(VALID_COMPANY_NUM))
                .thenReturn(false);
        Mockito.when(mockedUserService.emailExists(userView.getEmail()))
                .thenReturn(true);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.createUser(VALID_COMPANY_NUM, userView);
                });
    }

    @Test
    void createUserTest(){
        Mockito.when(mockedUserService.userExists(VALID_COMPANY_NUM))
                .thenReturn(false);
//        Mockito.when(mockedUserService.emailExistsForAnotherUser(userView.getEmail(), VALID_COMPANY_NUM))
//                .thenReturn(false);
        Mockito.when(mockedFacilityService.getFacilityByName(FACILITY_NAME)).thenReturn(facilityEntity);

        Mockito.when(mockedModelMapper.map(userView, UserEntity.class)).thenReturn(userToBeSaved);
        Mockito.when(mockedUserRepository.save(userToBeSaved)).thenReturn(userToBeSaved);
        Mockito.when(mockedModelMapper.map(userToBeSaved, UserViewDto.class)).thenReturn(userView);

        UserViewDto userCreated = testService.createUser(VALID_COMPANY_NUM, userView);

        Assertions.assertEquals(userCreated.getCompanyNum(), VALID_COMPANY_NUM );

    }


}
