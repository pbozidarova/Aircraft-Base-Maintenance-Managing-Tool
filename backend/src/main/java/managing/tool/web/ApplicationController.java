package managing.tool.web;

import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.service.AircraftSeedService;
import managing.tool.e_facility.service.FacilitySeedService;
import managing.tool.e_maintenance.service.MaintenanceSeedService;

import managing.tool.e_notification.service.NotificationService;
import managing.tool.e_notification.service.ReplyService;
import managing.tool.e_task.service.TaskSeedService;
import managing.tool.e_user.service.RoleSeedService;
import managing.tool.e_user.service.UserSeedService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
@AllArgsConstructor
public class ApplicationController implements CommandLineRunner {

    private final UserSeedService userSeedService;
    private final RoleSeedService roleSeedService;
    private final AircraftSeedService aircraftSeedService;
    private final FacilitySeedService facilitySeedService;
    private final MaintenanceSeedService maintenanceSeedService;
    private final TaskSeedService taskSeedService;
    private final ReplyService replySeedService;
    private final NotificationService notificationService;

    @Override
    public void run(String... args) throws Exception {

        this.seedData();
    }

    private void seedData() throws FileNotFoundException {
        this.roleSeedService.seedRoles();
        this.userSeedService.seedUsers();
        this.aircraftSeedService.seedAircraft();
        this.facilitySeedService.seedFacilities();
        this.taskSeedService.seedTasks();
        this.maintenanceSeedService.seedMaintenance();
        this.replySeedService.seedReplies();
        this.notificationService.generateMockupNotificationsOnInitialLaunch();

    }
}
