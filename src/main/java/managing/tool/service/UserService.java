package managing.tool.service;

import managing.tool.model.dto.view.UserViewDto;
import managing.tool.model.entity.UserEntity;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserService {

    UserViewDto findUser(String companyNum);

    List<UserViewDto> findAllUsers();

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();

    UserEntity findByCompanyNum(String companyNum);
}
