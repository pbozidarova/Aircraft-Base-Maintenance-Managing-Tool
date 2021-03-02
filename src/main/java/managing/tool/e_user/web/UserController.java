package managing.tool.e_user.web;

import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
