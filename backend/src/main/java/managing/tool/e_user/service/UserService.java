package managing.tool.e_user.service;

import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.model.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserViewDto findUser(String companyNum);

    Optional<UserDetailsDto> findUserDetails(String companyNum);

    List<UserViewDto> findAllUsers();

    UserEntity findByCompanyNum(String companyNum);
    UserEntity getRandomUser();
}
