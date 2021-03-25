package managing.tool.e_user.service;

import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserViewDto;

import java.util.List;
import java.util.Optional;

public interface UserCreateUpdateService {

    UserViewDto updateUser(UserViewDto userViewDto);
    UserViewDto createUser(UserViewDto userViewDto);

//    Optional<UserDetailsDto> findUserDetails(String companyNum);
//
//    List<UserViewDto> findAllUsers();
//
//    UserEntity findByCompanyNum(String companyNum);
//    UserEntity getRandomUser();
//
//    Boolean userExists(String companyNum);
//    Boolean emailExists(String email);
//    Boolean emailExistsForAnotherUser(String email, String companyNum);
}
