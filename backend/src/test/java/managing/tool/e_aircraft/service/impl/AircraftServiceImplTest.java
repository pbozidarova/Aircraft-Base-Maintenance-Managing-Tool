package managing.tool.e_aircraft.service.impl;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.repository.AircraftRepository;
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

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AircraftServiceImplTest {
    private final String VALID_REGISTRATION = "Valid_Reg";
    private final String INVALID_REGISTRATION = "Invalid_Reg";
    private final String SERIAL_NUMBER = "12345";
    private final String JWT_STRING = "jwt";


    private AircraftServiceImpl testService;
    private AircraftEntity aircraftEx;
    private AircraftEntity aircraftToBeSaved;
    private AircraftViewDto aircraftRequestData;
    private String jwt;

    @Mock
    AircraftRepository mockedAircraftRepository;
    @Mock
    ModelMapper mockedModelMapper;

    @BeforeEach
    void setUp(){
        testService = new AircraftServiceImpl(mockedAircraftRepository, mockedModelMapper);

        aircraftEx = new AircraftEntity();
        aircraftEx.setAircraftRegistration(VALID_REGISTRATION)
                    .setSerialNumber(SERIAL_NUMBER)
                    .setId(1L);

        aircraftToBeSaved = new AircraftEntity();
        aircraftToBeSaved.setAircraftRegistration(VALID_REGISTRATION)
                .setSerialNumber(SERIAL_NUMBER)
                .setId(1L);

        aircraftRequestData = new AircraftViewDto();
        aircraftRequestData.setAircraftRegistration(VALID_REGISTRATION);
        jwt = JWT_STRING;
    }

    @Test
    void findAllTest(){
        Mockito.when(mockedAircraftRepository.findAll()).thenReturn(List.of(aircraftEx));

        Assertions.assertEquals(1, testService.findAll().size());
    }

    @Test
    void getAircraftByRegistrationTest(){
        Mockito.when(mockedAircraftRepository.findByAircraftRegistration(VALID_REGISTRATION)).thenReturn(aircraftEx);

        Assertions.assertEquals(SERIAL_NUMBER, testService.getAircraftByRegistration(VALID_REGISTRATION).getSerialNumber());
    }

    @Test
    void aircraftExistsTest(){
        Mockito.when(mockedAircraftRepository.findByAircraftRegistration(VALID_REGISTRATION)).thenReturn(aircraftEx);

        Assertions.assertTrue(testService.aircraftExists(VALID_REGISTRATION));
    }

    @Test
    void noSuchAircraft(){
        Assertions.assertThrows(
                 NotFoundInDb.class, () -> {
                    testService.updateAircraft(INVALID_REGISTRATION, aircraftRequestData, jwt);
                });
    }

    @Test
    void updateAircraftTest(){
        Mockito.when(mockedAircraftRepository.findByAircraftRegistration(VALID_REGISTRATION)).thenReturn(aircraftEx);

        Mockito.when(mockedModelMapper.map(aircraftRequestData, AircraftEntity.class)).thenReturn(aircraftToBeSaved);
        Mockito.when(mockedModelMapper.map(aircraftToBeSaved, AircraftViewDto.class)).thenReturn(aircraftRequestData);

        Mockito.when(mockedAircraftRepository.save(aircraftToBeSaved)).thenReturn(aircraftToBeSaved);
        AircraftViewDto aircraftUpdated = testService.updateAircraft(VALID_REGISTRATION, aircraftRequestData, jwt);

        Assertions.assertEquals(aircraftUpdated.getAircraftRegistration(), aircraftToBeSaved.getAircraftRegistration());
    }


    @Test
    void aircraftAlreadyExists(){
        Mockito.when(mockedAircraftRepository.findByAircraftRegistration(VALID_REGISTRATION)).thenReturn(aircraftEx);

        Assertions.assertThrows(
                FoundInDb.class, () -> {
                    testService.createAircraft(VALID_REGISTRATION, aircraftRequestData, jwt);
                });
    }

    @Test
    void createAircraftTest(){
        Mockito.when(mockedAircraftRepository.findByAircraftRegistration(VALID_REGISTRATION)).thenReturn(null);

        Mockito.when(mockedModelMapper.map(aircraftRequestData, AircraftEntity.class)).thenReturn(aircraftToBeSaved);
        Mockito.when(mockedAircraftRepository.save(aircraftToBeSaved)).thenReturn(aircraftToBeSaved);
        Mockito.when(mockedModelMapper.map(aircraftToBeSaved, AircraftViewDto.class)).thenReturn(aircraftRequestData);

        AircraftViewDto aircraftCtd = testService.createAircraft(VALID_REGISTRATION, aircraftRequestData, jwt);

        Assertions.assertEquals(aircraftToBeSaved.getAircraftRegistration(), aircraftCtd.getAircraftRegistration());
    }


}
