package managing.tool.e_user.web;

import managing.tool.constants.GlobalConstants;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static managing.tool.constants.GlobalConstants.FRONTEND_URL;

@RestController
@RequestMapping("/users")
@CrossOrigin(FRONTEND_URL)
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public List<UserViewDto> allUsers(){
        return this.userService.findAllUsers();
    }

    @GetMapping("/{companyNum}")
    public UserViewDto user(@PathVariable String companyNum){
        return this.userService.findUser(companyNum);
    }
}
