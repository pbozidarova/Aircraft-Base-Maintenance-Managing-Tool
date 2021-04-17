package managing.tool.e_user.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.repository.RoleRepository;
import managing.tool.e_user.service.RoleSeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Random;

@Service
@Transactional
@AllArgsConstructor
public class RoleSeedServiceImpl implements RoleSeedService {

    private final RoleRepository roleRepository;

    @Override
    public void seedRoles() {
        if(this.rolesAreImported()) {
            return;
        }

        Arrays.stream(RoleEnum.values())
        .forEach(r -> {
            this.roleRepository.save(new RoleEntity(r));
        });
    }

    @Override
    public boolean rolesAreImported() {
        return this.roleRepository.count() > 0;
    }


}
