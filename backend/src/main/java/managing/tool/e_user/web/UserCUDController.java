package managing.tool.e_user.web;

import lombok.AllArgsConstructor;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserCreateUpdateService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static managing.tool.constants.GlobalConstants.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("/users")
@CrossOrigin(FRONTEND_URL)
@AllArgsConstructor
public class UserCUDController {
    private final UserService userService;
    private final UserCreateUpdateService userCreateUpdateService;
    private final FacilityService facilityService;

    @PutMapping("/{companyNum}/update")
    public ResponseEntity<UserViewDto> updateSingleUser(
            @PathVariable String companyNum, @RequestBody UserViewDto userDataForUpdate )  {

        if(!this.userService.userExists(companyNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, companyNum), "companyNum");
        }
        if(this.userService.emailExistsForAnotherUser(userDataForUpdate.getEmail(), companyNum)){
            throw new FoundInDb(String.format(FOUNDERROR, userDataForUpdate.getEmail()), "email");
        }

        UserViewDto userUpdated = this.userCreateUpdateService.updateUser(userDataForUpdate);

        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }

    @PutMapping("/{companyNum}/create")
    public ResponseEntity<UserViewDto> createSingleUser(
            @PathVariable String companyNum, @RequestBody UserViewDto userNew ) throws FoundInDb {

        if(this.userService.userExists(companyNum)){
            throw new FoundInDb(String.format(FOUNDERROR, companyNum), "companyNum");
        }
        if(this.userService.emailExists(userNew.getEmail())){
            throw new FoundInDb(String.format(FOUNDERROR, userNew.getEmail()), "email");
        }

        UserViewDto userCreated = this.userCreateUpdateService.createUser(userNew);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }


}
