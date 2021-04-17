package managing.tool.e_aircraft.web;

import managing.tool.AircraftBaseMaintenanceManagingToolApplication;
import managing.tool.config.WithMockCustomUser;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.e_aircraft.service.AircraftSeedService;
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

import java.io.FileNotFoundException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AircraftReadControllerTest {
    private static final String REQUEST_MAPPING_AIRCRAFT = "/aircraft";

    @Autowired
    private AircraftSeedService aircraftSeedService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp(){
        try {
            aircraftSeedService.seedAircraft();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    @WithMockCustomUser(companyNum = "N90909")
    void returnAllAircraft() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING_AIRCRAFT +"/all"))
                .andExpect(status().isOk());
    }
}
