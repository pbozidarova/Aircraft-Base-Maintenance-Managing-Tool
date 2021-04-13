package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final Random random;
    private final ServiceUtil serviceUtil;

    @Override
    public UserViewDto findUser(String companyNum) {
        UserEntity userEntity = this.userRepository.findByCompanyNum(companyNum);
        return serviceUtil.buildUserVMRelationalStrings(userEntity);
    }


    @Override
    public Optional<UserDetailsDto> findUserDetails(String companyNum) {
        UserEntity userEntity = this.userRepository.findByCompanyNum(companyNum);
        UserDetailsDto userDetailsDto = this.modelMapper.map(
                userEntity, UserDetailsDto.class
        );

        Set<RoleEntity> roleSet = new HashSet<>();
        userEntity.getRoles().stream().forEach(role -> roleSet.add(role));
        userDetailsDto.setRoles(roleSet);

        return Optional.of(userDetailsDto);
    }

    @Override
    public List<UserViewDto> findAllUsers() {

        return this.userRepository.findAll()
                .stream()
                .map(serviceUtil::buildUserVMRelationalStrings)
                .collect(Collectors.toList());
    }


    @Override
    public UserEntity findByCompanyNum(String companyNum) {
        return this.userRepository.findByCompanyNum(companyNum);
    }

    @Override
    public Boolean userExists(String companyNum) {
        return this.findByCompanyNum(companyNum) != null;
    }

    @Override
    public Boolean emailExists(String email) {
        return this.userRepository.findByEmail(email) != null;
    }

    @Override
    public Boolean emailExistsForAnotherUser(String email, String companyNum) {
        return this.emailExists(email) && !this.userRepository.findByEmail(email).getCompanyNum().equals(companyNum);
    }

    @Override
    public UserEntity getRandomUser() {
        long maxRandomNumber =  this.userRepository.count();

        long randomId = random.nextInt((int) maxRandomNumber) + 1;

        return this.userRepository.getOne(randomId);
    }

    @Override
    public List<UserEntity> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public void saveUser(UserEntity user) {
        this.userRepository.saveAndFlush(user);
    }




}
