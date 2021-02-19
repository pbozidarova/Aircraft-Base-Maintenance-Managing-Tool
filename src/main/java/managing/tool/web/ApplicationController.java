package managing.tool.web;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationController implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        this.seedData();
    }

    private void seedData() {

    }
}
