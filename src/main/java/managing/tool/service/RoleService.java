package managing.tool.service;

import managing.tool.model.entity.RoleEntity;
import managing.tool.model.entity.enumeration.RoleEnum;

public interface RoleService {
    void seedRoles();
    RoleEntity findByName(RoleEnum role);
    boolean rolesAreImported();
}
