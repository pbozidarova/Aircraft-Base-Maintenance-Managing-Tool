package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import static managing.tool.constants.GlobalConstants.NOTFOUND_SELECT_ERROR;

@Service
@AllArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {
    private final UserService userService;
    private final RoleService roleService;


    @Override
    public void validateIfUserExist(String companyNumOfManager) {
        System.out.println(companyNumOfManager);
        if(!this.userService.userExists(companyNumOfManager)){
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, companyNumOfManager, "employee from the list!"),
                    "companyNumOfManager"
            );
        }
    }

    @Override
    public void validateIfRolesExist(String roles){
        Arrays.stream(roles.split(", "))
                .forEach(role -> {
                    System.out.println(role);
                    if(!this.roleService.roleExists(RoleEnum.valueOf(role))){
                        throw new NotFoundInDb(
                                String.format(NOTFOUND_SELECT_ERROR, role, "role"),
                                "role"
                        );
                    }
                });

    }


}
