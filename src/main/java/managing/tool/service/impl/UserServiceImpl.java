package managing.tool.service.impl;

import com.google.gson.Gson;
import managing.tool.model.dto.seed.UserSeedDto;
import managing.tool.model.dto.view.UserViewDto;
import managing.tool.model.entity.RoleEntity;
import managing.tool.model.entity.UserEntity;
import managing.tool.model.entity.enumeration.RoleEnum;
import managing.tool.repository.UserRepository;
import managing.tool.service.RoleService;
import managing.tool.service.UserService;
import org.apache.tomcat.jni.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.USERS_MOCK_DATA_PATH;

@Service
public class UserServiceImpl implements UserService {

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(Gson gson, ModelMapper modelMapper, UserRepository userRepository, RoleService roleService) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public UserViewDto findUser(String companyNum) {
        return this.modelMapper
                .map(this.userRepository.findByCompanyNum(companyNum),
                        UserViewDto.class);
    }

    @Override
    public List<UserViewDto> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> this.modelMapper.map(u, UserViewDto.class))
                .collect(Collectors.toList());
    }

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
                    RoleEntity role = this.roleService.findByName(RoleEnum.valueOf(uDto.getRole().toUpperCase()));
                    Set<RoleEntity> roleSet = new HashSet<>();
                    roleSet.add(role);
                    user.setRole(roleSet);
                    user.setCompanyNum(uDto.getCompanyNum());
                    this.userRepository.saveAndFlush(user);
                });

    }

    @Override
    public boolean userAreImported() {
        return this.userRepository.count() > 0;
    }

    @Override
    public UserEntity findByCompanyNum(String companyNum) {

        return this.userRepository.findByCompanyNum(companyNum);
    }


}
