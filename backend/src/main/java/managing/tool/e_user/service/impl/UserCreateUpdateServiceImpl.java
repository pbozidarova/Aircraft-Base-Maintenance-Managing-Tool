package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserCreateUpdateService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserCreateUpdateServiceImpl implements UserCreateUpdateService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final FacilityService facilityService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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

        userEntity.setUpdatedOn(LocalDateTime.now());


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

        user.setUpdatedOn(LocalDateTime.now());
        user.setCreatedOn(LocalDateTime.now());

        this.userRepository.save(user);

        return this.modelMapper.map(user, UserViewDto.class);

    }

}
