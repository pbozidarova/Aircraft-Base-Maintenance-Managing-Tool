package managing.tool.e_aircraft.web;

import managing.tool.AircraftBaseMaintenanceManagingToolApplication;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.e_aircraft.service.AircraftService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
////@RunWith(SpringRunner.class)
//@ContextConfiguration(classes= AircraftBaseMaintenanceManagingToolApplication.class)
//@WebMvcTest(AircraftReadController.class)
//@ExtendWith(MockitoExtension.class)
public class AircraftReadControllerTest {

    private static final String AIRCRAFT_REGISTRATION = "LZ_Reg";
    private final String JWT_STRING = "jwt";

    @Autowired
    private AircraftRepository aircraftRepository;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        AircraftEntity aircraftEntity = new AircraftEntity();
        aircraftEntity.setAircraftRegistration(AIRCRAFT_REGISTRATION);

        aircraftRepository.save(aircraftEntity);
    }

    @Test
    @WithMockUser(value = "spring")
    void returnAllAircraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/all"))
                .andExpect(status().isOk());
    }
}
