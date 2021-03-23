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
    private final RoleService roleService;
    private final FacilityService facilityService;
    private final Random random;
    private final PasswordEncoder passwordEncoder;



    @Override
    public UserViewDto findUser(String companyNum) {
        UserEntity userEntity = this.userRepository.findByCompanyNum(companyNum);

        UserViewDto userView = this.modelMapper
                .map(userEntity,
                        UserViewDto.class);
        userView.setFacility(userEntity.getFacility().getName());

        userView.setRoles( userView
                .getRoles()
                .replace("[", "")
                .replace("]", "")
        );
        return userView;
    }

    @Override
    public UserViewDto updateUser(UserViewDto userViewDto) {
        UserEntity userEntity = this.modelMapper.map(userViewDto, UserEntity.class);

        UserEntity existingUser =  this.userRepository
                .findByCompanyNum(userViewDto.getCompanyNum());

        userEntity.setId(existingUser.getId());
        userEntity.setPassword(existingUser.getPassword());

        FacilityEntity facilityEntity = this.facilityService.getFacilityByName(userViewDto.getFacility());

        userEntity.setFacility(
                this.facilityService.getFacilityByName(userViewDto.getFacility())
        );

        allocateRoles(userEntity, userViewDto.getRoles());

        userEntity.setUpdatedOn(Instant.now());


        return this.modelMapper.map(this.userRepository.save( userEntity) , UserViewDto.class);
    }

    private void allocateRoles(UserEntity user, String rolesString) {
        Set<RoleEntity> roleSet = new HashSet<>();
        Arrays.stream(rolesString.split(", "))
                .forEach( r -> {
                    RoleEntity role = this.roleService
                            .findByName(RoleEnum.valueOf(r));
                    roleSet.add(role);
                });

        user.setRoles(roleSet);
    }

    @Override
    public UserViewDto createUser(UserViewDto userViewDto) {
        UserEntity user =  this.modelMapper.map(userViewDto, UserEntity.class );

        user.setPassword(passwordEncoder.encode(GlobalConstants.DUMMY_PASS));

        user.setFacility(
                this.facilityService.getFacilityByName(userViewDto.getFacility())
        );

        allocateRoles(user, userViewDto.getRoles());

        user.setUpdatedOn(Instant.now());
        user.setCreatedOn(Instant.now());

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserViewDto.class);

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
                .map(u -> {
                    UserViewDto userView = this.modelMapper.map(u, UserViewDto.class);

                    userView.setFacility(u.getFacility().getName());

                    userView.setRoles( userView
                                            .getRoles()
                                            .replace("[", "")
                                            .replace("]", "")
                    );

                    return userView;
                })
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
    public UserEntity getRandomUser() {
        long maxRandomNumber =  this.userRepository.count();

        long randomId = random.nextInt((int) maxRandomNumber) + 1;

        return this.userRepository.getOne(randomId);
    }


}
