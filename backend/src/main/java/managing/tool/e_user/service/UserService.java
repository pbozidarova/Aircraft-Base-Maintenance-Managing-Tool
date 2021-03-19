package managing.tool.e_user.service;

import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserAllViewDto;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserSingleViewDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserSingleViewDto findUser(String companyNum);

    Optional<UserDetailsDto> findUserDetails(String companyNum);

    List<UserAllViewDto> findAllUsers();

    UserEntity findByCompanyNum(String companyNum);
    UserEntity getRandomUser();
}
