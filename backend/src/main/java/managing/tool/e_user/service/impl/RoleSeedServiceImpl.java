package managing.tool.e_user.service.impl;

import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.repository.RoleRepository;
import managing.tool.e_user.service.RoleSeedService;
import managing.tool.e_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class RoleSeedServiceImpl implements RoleSeedService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleSeedServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

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
