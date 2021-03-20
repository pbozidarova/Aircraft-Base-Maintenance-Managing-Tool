package managing.tool.e_user.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
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
    private final Random random;


    @Override
    public void seedUsers() throws FileNotFoundException {
        if(this.userAreImported()){
            return;
        }

        //TODO THROW ERROR FROM FILE READER
        UserSeedDto[] dtos = this.gson
                .fromJson(new FileReader(USERS_MOCK_DATA_PATH), UserSeedDto[].class);

        System.out.println();

        Arrays.stream(dtos)
                .forEach(uDto -> {
                    UserEntity user = this.modelMapper.map(uDto, UserEntity.class);
                    //TODO randomly allocate ADMIN or USER and ENG or MECH

                    RoleEntity role = this.roleService.findByName(RoleEnum.valueOf(uDto.getRole().toUpperCase()));
                    Set<RoleEntity> roleSet = new HashSet<>();
                    roleSet.add(role);
                    user.setRoles(roleSet);
                    user.setCompanyNum(uDto.getCompanyNum());

                    user.setPassword(passwordEncoder.encode(GlobalConstants.DUMMY_PASS));
                    this.userRepository.saveAndFlush(user);
                });

    }

    @Override
    public boolean userAreImported() {
        return this.userRepository.count() > 0;
    }



}
