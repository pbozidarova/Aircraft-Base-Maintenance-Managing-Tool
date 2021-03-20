package managing.tool.e_user.service.impl;

import com.google.gson.Gson;
import managing.tool.e_user.model.dto.UserDetailsDto;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.RoleService;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    public UserEntity getRandomUser() {
        long maxRandomNumber =  this.userRepository.count();

        long randomId = random.nextInt((int) maxRandomNumber) + 1;

        return this.userRepository.getOne(randomId);
    }


}
