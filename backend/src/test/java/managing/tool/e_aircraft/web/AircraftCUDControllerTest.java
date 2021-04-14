package managing.tool.e_aircraft.web;

import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.repository.AircraftRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AircraftCUDControllerTest {
    private static final String REQUEST_MAPPING_AIRCRAFT = "/aircraft";
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
    void createAircraftTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(REQUEST_MAPPING_AIRCRAFT +"/{registration}/create", AIRCRAFT_REGISTRATION)
//                .with(csrf())
        )
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(value = "spring")
    void updateAircraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(REQUEST_MAPPING_AIRCRAFT +"/{registration}/update", AIRCRAFT_REGISTRATION)
//                .with(csrf())
        )
                .andExpect(status().isOk());

    }
}
