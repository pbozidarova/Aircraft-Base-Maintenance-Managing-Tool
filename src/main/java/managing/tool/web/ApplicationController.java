package managing.tool.web;

import managing.tool.service.AircraftService;
import managing.tool.service.FacilityService;
import managing.tool.service.RoleService;
import managing.tool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final AircraftService aircraftService;
    private final FacilityService facilityService;

    @Autowired
    public ApplicationController(UserService userService, RoleService roleService, AircraftService aircraftService, FacilityService facilityService) {
        this.userService = userService;
        this.roleService = roleService;
        this.aircraftService = aircraftService;
        this.facilityService = facilityService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.seedData();
    }

    private void seedData() throws FileNotFoundException {
        this.roleService.seedRoles();
        this.userService.seedUsers();
        this.aircraftService.seedAircraft();
        this.facilityService.seedFacilities();
    }
}
