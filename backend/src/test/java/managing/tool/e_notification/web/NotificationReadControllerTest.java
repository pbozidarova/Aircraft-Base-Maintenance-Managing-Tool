package managing.tool.e_notification.web;

import managing.tool.config.WithMockCustomUser;
import managing.tool.e_maintenance.service.MaintenanceSeedService;
import managing.tool.e_notification.service.NotificationService;
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
public class NotificationReadControllerTest {
    private static final String REQUEST_MAPPING_NOTIFICATION = "/notifications";

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        notificationService.generateMockupNotificationsOnInitialLaunch();
    }

    @Test
    @WithMockCustomUser(companyNum = "N90909")
    void returnAllMaintenance() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING_NOTIFICATION +"/all"))
                .andExpect(status().isOk());
    }
}
