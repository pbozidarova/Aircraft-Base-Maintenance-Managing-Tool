package managing.tool.e_user.service.impl;

import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import managing.tool.e_user.repository.RoleRepository;
import managing.tool.e_user.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
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
    public RoleEntity findByName(RoleEnum role) {

        return this.roleRepository.findByName(role);
    }

    @Override
    public boolean rolesAreImported() {
        return this.roleRepository.count() > 0;
    }
}