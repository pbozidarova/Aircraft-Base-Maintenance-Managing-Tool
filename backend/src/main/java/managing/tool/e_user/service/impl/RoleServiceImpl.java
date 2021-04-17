package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.repository.RoleRepository;
import managing.tool.e_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Random random;

    @Override
    public RoleEntity findByName(String role) {
        return this.roleRepository.findByName(RoleEnum.valueOf(role));
    }

    @Override
    public Boolean roleExists(String role) {
        return Arrays.stream(RoleEnum.values()).anyMatch(roleEnum -> roleEnum.toString().equals(role));
//        return this.findByName(role) != null;
    }


    @Override
    public boolean isMechanic() {
        return this.random.nextInt(10) > 5;
    }

}
