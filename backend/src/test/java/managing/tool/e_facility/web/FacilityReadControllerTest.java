package managing.tool.e_facility.web;

import lombok.AllArgsConstructor;
import managing.tool.config.WithMockCustomUser;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_facility.service.FacilitySeedService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileNotFoundException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class FacilityReadControllerTest {
    private static final String REQUEST_MAPPING_FACILITY = "/facilities";

    @Autowired
    private FacilitySeedService facilitySeedService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        facilitySeedService.seedFacilities();

    }

    @Test
    @WithMockCustomUser(companyNum = "N90909")
    void returnAllFacility() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING_FACILITY +"/all"))
                .andExpect(status().isOk());
    }
}
