package managing.tool.service.impl;

import com.google.gson.Gson;
import managing.tool.model.dto.seed.UserSeedDto;
import managing.tool.model.entity.User;
import managing.tool.repository.UserRepository;
import managing.tool.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import static managing.tool.constants.GlobalConstants.USERS_MOCK_DATA_PATH;

@Service
public class UserServiceImpl implements UserService {

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(Gson gson, ModelMapper modelMapper, UserRepository userRepository) {
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers() throws FileNotFoundException {
        //TODO THROW ERROR FROM FILE READER
        UserSeedDto[] dtos = this.gson
                .fromJson(new FileReader(USERS_MOCK_DATA_PATH), UserSeedDto[].class);

        System.out.println();

        Arrays.stream(dtos)
                .forEach(uDto -> {
                    User user = this.modelMapper.map(uDto, User.class);

                    this.userRepository.saveAndFlush(user);
                });

    }
}
