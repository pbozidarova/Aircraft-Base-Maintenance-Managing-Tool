package managing.tool.service.impl;

import managing.tool.model.entity.RoleEntity;
import managing.tool.model.entity.enumeration.RoleEnum;
import managing.tool.repository.RoleRepository;
import managing.tool.service.RoleService;
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
