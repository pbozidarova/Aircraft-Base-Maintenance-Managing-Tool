package managing.tool.e_user.service;

import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserAllViewDto;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserSingleViewDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserSeedService {

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();


}
