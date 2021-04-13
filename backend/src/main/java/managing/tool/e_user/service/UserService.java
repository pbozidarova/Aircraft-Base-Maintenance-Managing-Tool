package managing.tool.e_user.service;

import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.model.UserEntity;
import org.apache.catalina.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserViewDto findUser(String companyNum);
//    UserViewDto updateUser(UserViewDto userViewDto);
//    UserViewDto createUser(UserViewDto userViewDto);
    UserViewDto buildUserVMRelationalStrings(UserEntity u);
    UserEntity identifyingUserFromToken(String token);
    String userViewStringBuild(UserEntity userEntity);
    String companyNumFromUserString(String userViewStringBuilt);

    Optional<UserDetailsDto> findUserDetails(String companyNum);

    List<UserViewDto> findAllUsers();

    UserEntity findByCompanyNum(String companyNum);
    UserEntity getRandomUser();

    Boolean userExists(String companyNum);
    Boolean emailExists(String email);
    Boolean emailExistsForAnotherUser(String email, String companyNum);

    List<UserEntity> findAll();
    void saveUser(UserEntity user);



}
