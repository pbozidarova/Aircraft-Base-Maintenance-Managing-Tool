package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.service.FacilityValidationService;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserCreateUpdateService;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static managing.tool.constants.GlobalConstants.*;

@Service
@AllArgsConstructor
public class UserCreateUpdateServiceImpl implements UserCreateUpdateService {

    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final UserService userService;
    private final FacilityService facilityService;
    private final FacilityValidationService facilityValidationService;
    private final UserValidationService userValidationService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ServiceUtil serviceUtil;

    @Override
    public UserViewDto updateUser(String companyNum, UserViewDto userRequestUpdateData) {
        if(!this.userService.userExists(companyNum)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, companyNum), "companyNum");
        }
        if(this.userService.emailExistsForAnotherUser(userRequestUpdateData.getEmail(), companyNum)){
            throw new FoundInDb(String.format(FOUNDERROR, userRequestUpdateData.getEmail()), "email");
        }
        //SELECT FIELDS VALIDATION
        facilityValidationService.validateIfFacilityExists(userRequestUpdateData.getFacility());
        userValidationService.validateIfRolesExist(userRequestUpdateData.getRoles());

        UserEntity userNewData = this.modelMapper.map(userRequestUpdateData, UserEntity.class);
        UserEntity existingUser =  this.userRepository
                .findByCompanyNum(userRequestUpdateData.getCompanyNum());
        FacilityEntity facilityEntity = this
                .facilityService.getFacilityByName(userRequestUpdateData.getFacility());

        userNewData.setPassword(existingUser.getPassword())
                .setFacility(facilityEntity)
                .setId(existingUser.getId())
                .setUpdatedOn(LocalDateTime.now());

        allocateRoles(userNewData, userRequestUpdateData.getRoles());

        return this.modelMapper.map(this.userRepository.save(userNewData) , UserViewDto.class);
    }


    @Override
    public UserViewDto createUser(String companyNum, UserViewDto userRequestCreateData) {
        if(this.userService.userExists(companyNum)){
            throw new FoundInDb(String.format(FOUNDERROR, companyNum), "companyNum");
        }
        if(this.userService.emailExists(userRequestCreateData.getEmail())){
            throw new FoundInDb(String.format(FOUNDERROR, userRequestCreateData.getEmail()), "email");
        }

        //SELECT FIELDS VALIDATION
        facilityValidationService.validateIfFacilityExists(userRequestCreateData.getFacility());
        userValidationService.validateIfRolesExist(userRequestCreateData.getRoles());

        UserEntity user =  this.modelMapper.map(userRequestCreateData, UserEntity.class );

        user.setPassword(passwordEncoder.encode(GlobalConstants.DUMMY_PASS))
                .setFacility(this.facilityService.getFacilityByName(userRequestCreateData.getFacility()))
                .setCreatedOn(LocalDateTime.now());

        allocateRoles(user, userRequestCreateData.getRoles());

        return this.modelMapper.map( this.userRepository.save(user), UserViewDto.class);

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





}
