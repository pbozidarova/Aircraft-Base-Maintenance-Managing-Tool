package managing.tool.e_user.service.impl;

import com.google.gson.Gson;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.dto.UserSeedDto;
import managing.tool.e_user.model.dto.UserAllViewDto;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.dto.UserSingleViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.USERS_MOCK_DATA_PATH;

@Service
public class UserServiceImpl implements UserService {

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final Random random;

    @Autowired
    public UserServiceImpl(Gson gson, ModelMapper modelMapper, UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, Random random) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
        this.random = random;
    }

    @Override
    public UserSingleViewDto findUser(String companyNum) {
        UserEntity userEntity = this.userRepository.findByCompanyNum(companyNum);

        UserSingleViewDto userView = this.modelMapper
                .map(this.userRepository.findByCompanyNum(companyNum),
                        UserSingleViewDto.class);
        for (RoleEntity role : userEntity.getRoles()) {
            if(role.getName() == RoleEnum.ADMIN ) userView.setADMIN(true) ;
            if(role.getName() == RoleEnum.USER) userView.setUSER(true) ;
            if(role.getName() == RoleEnum.ENGINEER ) userView.setENGINEER(true);
            if(role.getName() == RoleEnum.MECHANIC ) userView.setMECHANIC(true);
        }


        return userView;
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
    public List<UserAllViewDto> findAllUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(u -> {
                    UserAllViewDto userView = this.modelMapper.map(u, UserAllViewDto.class);

                    String uName = String.format("%s %s", u.getFirstName(), u.getLastName());
                    userView.setNames(uName);

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

    @Override
    public UserEntity findByCompanyNum(String companyNum) {

        return this.userRepository.findByCompanyNum(companyNum);
    }

    @Override
    public UserEntity getRandomUser() {
        long maxRandomNumber =  this.userRepository.count();

        long randomId = random.nextInt((int) maxRandomNumber) + 1;

        return this.userRepository.getOne(randomId);
    }


}
