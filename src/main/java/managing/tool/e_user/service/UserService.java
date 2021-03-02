package managing.tool.e_user.service;

import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.model.UserEntity;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    UserViewDto findUser(String companyNum);

    List<UserViewDto> findAllUsers();

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();

    UserEntity findByCompanyNum(String companyNum);
}
