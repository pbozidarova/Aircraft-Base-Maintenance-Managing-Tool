package managing.tool.util;

import lombok.AllArgsConstructor;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ServiceUtil {

    private final JwtUtil jwtUtil;
    private final UserService userService;

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

}
