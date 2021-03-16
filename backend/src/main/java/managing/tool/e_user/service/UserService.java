package managing.tool.e_user.service;

import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.model.UserEntity;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserViewDto findUser(String companyNum);

    UserDetailsDto findUserDetails(String companyNum);

    List<UserViewDto> findAllUsers();

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();

    UserEntity findByCompanyNum(String companyNum);
}
