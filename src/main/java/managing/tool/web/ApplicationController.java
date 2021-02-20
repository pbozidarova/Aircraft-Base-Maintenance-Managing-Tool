package managing.tool.web;

import managing.tool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.FileNotFoundException;

@Controller
public class ApplicationController implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public ApplicationController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.seedData();
    }

    private void seedData() throws FileNotFoundException {
        this.userService.seedUsers();
    }
}
