package managing.tool.web;

import managing.tool.model.dto.view.UserViewDto;
import managing.tool.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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
