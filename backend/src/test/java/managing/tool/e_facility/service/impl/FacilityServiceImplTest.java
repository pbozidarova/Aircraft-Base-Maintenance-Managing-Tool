package managing.tool.e_facility.service.impl;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import managing.tool.util.ServiceUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class FacilityServiceImplTest {
    private final String VALID_FACILITY_NAME = "Valid test facility name";
    private final String INVALID_FACILITY_NAME = "Invalid test facility name";
    private final String JWT_STRING = "jwt";

    private FacilityServiceImpl testService;
    private FacilityViewDto facilityRequestData;
    private FacilityEntity facilityExisting;
    private FacilityEntity facilityToBeSaved;
    private UserEntity user;
    private UserViewDto userViewDto;

    @Mock
    FacilityRepository mockedFacilityRepository;
    @Mock
    ModelMapper mockedModelMapper;
    @Mock
    UserService mockedUserService;
    @Mock
    UserValidationService mockedUserValidationService;
    @Mock
    ServiceUtil mockedServiceUtil;

    @BeforeEach
    void setUp(){
        testService = new FacilityServiceImpl(mockedFacilityRepository, mockedModelMapper, mockedUserService, mockedUserValidationService, mockedServiceUtil);
        user = new UserEntity();

        facilityRequestData = new FacilityViewDto();
        facilityRequestData.setName(VALID_FACILITY_NAME)
                            .setManager("N1234 - ");
        facilityExisting = new FacilityEntity();
        facilityExisting.setName(VALID_FACILITY_NAME)
                        .setEmployees(Set.of(user))
                        .setId(1L);
        facilityToBeSaved = new FacilityEntity();
        facilityToBeSaved.setName(VALID_FACILITY_NAME)
                .setEmployees(Set.of(user))
                .setId(1L);


    }

    @Test
    void noSuchFacility(){
        Assertions.assertThrows(
                NotFoundInDb.class, () -> {
                    testService.updateFacility(INVALID_FACILITY_NAME, facilityRequestData, JWT_STRING);
                });
    }

    @Test
    void updateFacilityTest(){
        Mockito.when(mockedFacilityRepository.findByName(VALID_FACILITY_NAME)).thenReturn(facilityExisting);
        Mockito.when(mockedServiceUtil.companyNumFromUserString("N1234 - ")).thenReturn("N1234");
        Mockito.when(mockedUserService.findByCompanyNum("N1234")).thenReturn(user);

        Mockito.when(mockedModelMapper.map(facilityRequestData, FacilityEntity.class)).thenReturn(facilityToBeSaved);
        Mockito.when(mockedModelMapper.map(facilityToBeSaved, FacilityViewDto.class)).thenReturn(facilityRequestData);

        Mockito.when(mockedFacilityRepository.save(facilityToBeSaved)).thenReturn(facilityToBeSaved);
        FacilityViewDto facilityUpdated = testService.updateFacility(VALID_FACILITY_NAME, facilityRequestData, JWT_STRING);

        Assertions.assertEquals(facilityUpdated.getName(), VALID_FACILITY_NAME);
    }

    @Test
    void facilityAlreadyExists(){
        Mockito.when(mockedFacilityRepository.findByName(VALID_FACILITY_NAME)).thenReturn(facilityExisting);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.createFacility(VALID_FACILITY_NAME, facilityRequestData, JWT_STRING);
                });
    }

    @Test
    void createFacilityTest(){
        Mockito.when(mockedFacilityRepository.findByName(VALID_FACILITY_NAME)).thenReturn(null);
        Mockito.when(mockedServiceUtil.companyNumFromUserString("N1234 - ")).thenReturn("N1234");
        Mockito.when(mockedUserService.findByCompanyNum("N1234")).thenReturn(user);

        Mockito.when(mockedModelMapper.map(facilityRequestData, FacilityEntity.class)).thenReturn(facilityToBeSaved);
        Mockito.when(mockedFacilityRepository.save(facilityToBeSaved)).thenReturn(facilityToBeSaved);
        Mockito.when(mockedModelMapper.map(facilityToBeSaved, FacilityViewDto.class)).thenReturn(facilityRequestData);

        FacilityViewDto facilityCreated = testService.createFacility(VALID_FACILITY_NAME, facilityRequestData, JWT_STRING);

        Assertions.assertEquals(facilityToBeSaved.getName(), facilityCreated.getName());

    }

    @Test
    void findAllUsersByFacilityNameTest(){
        Mockito.when(mockedFacilityRepository.findByName(VALID_FACILITY_NAME))
                .thenReturn(facilityExisting);
        Mockito.when(mockedServiceUtil.buildUserVMRelationalStrings(user)).thenReturn(userViewDto);


        Assertions.assertEquals(1, testService.findAllUsersByFacilityName(VALID_FACILITY_NAME).size());
    }

    @Test
    void findAllFacilitiesTest(){
        Mockito.when(mockedFacilityRepository.findAll()).thenReturn(List.of(facilityExisting));
        Mockito.when(mockedModelMapper.map(facilityExisting, FacilityViewDto.class)).thenReturn(facilityRequestData);

        Assertions.assertEquals(1, testService.findAll().size());
    }

    @Test
    void getFacilityByName(){
        Mockito.when(mockedFacilityRepository.findByName(VALID_FACILITY_NAME)).thenReturn(facilityExisting);

        Assertions.assertEquals(VALID_FACILITY_NAME, testService.getFacilityByName(VALID_FACILITY_NAME).getName());
    }

    @Test
    void facilityExistsTest(){
        Mockito.when(mockedFacilityRepository.findByName(VALID_FACILITY_NAME))
                .thenReturn(facilityExisting);

        Assertions.assertTrue(testService.facilityExists(VALID_FACILITY_NAME));
    }

}
