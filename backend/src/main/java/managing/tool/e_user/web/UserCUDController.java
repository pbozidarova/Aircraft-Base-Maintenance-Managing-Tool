package managing.tool.e_user.web;

import lombok.AllArgsConstructor;
import managing.tool.aop.TrackCreation;
import managing.tool.aop.TrackUpdating;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserCreateUpdateService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotAuthorized;
import managing.tool.exception.NotFoundInDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    @TrackUpdating(updatingMethod = "updateUser")
    @PutMapping("/{companyNum}/update")
    public ResponseEntity<UserViewDto> updateUser(
            @PathVariable String companyNum,
            @RequestBody UserViewDto userRequestUpdateData,
            @AuthenticationPrincipal User principal )  {

        if(principal.getAuthorities().toString().contains("ROLE_ADMIN")){
            UserViewDto userUpdated = this.userCreateUpdateService.updateUser(companyNum, userRequestUpdateData);

            return new ResponseEntity<>(userUpdated, HttpStatus.OK);
        }else {
            throw new NotAuthorized(String.format(NOT_AUTHORIZED_ERROR, "an ADMIN"), "authority");
        }
    }

    @TrackCreation(creatingMethod = "createUser")
    @PutMapping("/{companyNum}/create")
    public ResponseEntity<UserViewDto> createUser(
            @PathVariable String companyNum,
            @RequestBody UserViewDto userRequestCreateData,
            @AuthenticationPrincipal User principal) {

        if(principal.getAuthorities().toString().contains("ROLE_ADMIN")){
            UserViewDto userCreated = this.userCreateUpdateService.createUser( companyNum, userRequestCreateData);

            return new ResponseEntity<>(userCreated, HttpStatus.OK);
        }else {
            throw new NotAuthorized(String.format(NOT_AUTHORIZED_ERROR, "an ADMIN"), "authority");
        }
    }


}
