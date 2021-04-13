package managing.tool.util;

import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.NotFoundInDb;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static managing.tool.constants.GlobalConstants.NOTFOUND_SELECT_ERROR;

@Service
@AllArgsConstructor
public class ServiceUtil {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserEntity identifyingUserFromToken(String token){
        String companyNum = this.jwtUtil.extractUsername(token.replace("Bearer ", ""));
        return this.userService.findByCompanyNum(companyNum);
    }

    public String userViewStringBuild(UserEntity userEntity){
        return String.format("%s - %s, %s",  userEntity.getCompanyNum(), userEntity.getLastName(), userEntity.getFirstName());
    }

    public String companyNumFromUserString(String userViewStringBuilt){
        return userViewStringBuilt.split(" - ")[0];
    }


    public UserViewDto buildUserVMRelationalStrings(UserEntity u){
        UserViewDto userView = this.modelMapper.map(u, UserViewDto.class);

        userView.setFacility(u.getFacility().getName());

        userView.setRoles( userView
                .getRoles()
                .replace("[", "")
                .replace("]", "")
        );

        return userView;

    }



}
