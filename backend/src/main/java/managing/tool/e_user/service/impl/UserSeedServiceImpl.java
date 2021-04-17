package managing.tool.e_user.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.service.FacilitySeedService;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.dto.UserSeedDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserSeedService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.*;

import static managing.tool.constants.GlobalConstants.USERS_MOCK_DATA_PATH;

@Service
@AllArgsConstructor
public class UserSeedServiceImpl implements UserSeedService {

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void seedUsers() {
        if(this.userAreImported()){
            return;
        }

        try {
            UserSeedDto[] dtos = this.gson
                    .fromJson(new FileReader(USERS_MOCK_DATA_PATH), UserSeedDto[].class);

            Arrays.stream(dtos)
                    .forEach(uDto -> {
                        UserEntity user = this.modelMapper.map(uDto, UserEntity.class);
                        //TODO randomly allocate ADMIN or USER and ENG or MECH

                        Set<RoleEntity> roleSet = new HashSet<>();
                        RoleEntity authority = this.roleService.findByName(uDto.getRole().toUpperCase());
                        roleSet.add(authority);
                        allocateRole(roleSet, uDto.getCompanyNum());
                        user.setRoles(roleSet)
                            .setCompanyNum(uDto.getCompanyNum())
                            .setPassword(passwordEncoder.encode(GlobalConstants.DUMMY_PASS))
                            .setCreatedOn(LocalDateTime.now());

                        this.userRepository.saveAndFlush(user);
                    });
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Set<RoleEntity> allocateRole(Set<RoleEntity> roleSet, String companyNum){
        RoleEnum role;

        if(companyNum.equals("N90909")){
           role = RoleEnum.ENGINEER;
        }else if(companyNum.equals("N70707")){
            role = RoleEnum.MECHANIC;
        }else {
           role = this.roleService.isMechanic() ? RoleEnum.MECHANIC : RoleEnum.ENGINEER;
        }

        roleSet.add(this.roleService.findByName(role.toString()));

        return roleSet;
    }

    @Override
    public boolean userAreImported() {
        return this.userRepository.count() > 0;
    }

}
