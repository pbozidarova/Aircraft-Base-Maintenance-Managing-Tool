package managing.tool.e_user.web;

import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost")
@RestController
@RequestMapping("/users")
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
