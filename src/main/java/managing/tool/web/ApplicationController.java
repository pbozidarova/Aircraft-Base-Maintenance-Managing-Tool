package managing.tool.web;

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

    @Autowired
    public ApplicationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {

        this.seedData();
    }

    private void seedData() throws FileNotFoundException {
        if(!this.roleService.rolesAreImported()){
            this.roleService.seedRoles();
        }
        if(!this.userService.userAreImported()){
            this.userService.seedUsers();
        }

    }
}
