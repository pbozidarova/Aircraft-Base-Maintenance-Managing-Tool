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


    public UserEntity userCreatingTheChange(String token){
        String companyNum = this.jwtUtil.extractUsername(token.replace("Bearer ", ""));
        return this.userService.findByCompanyNum(companyNum);
    }

}
