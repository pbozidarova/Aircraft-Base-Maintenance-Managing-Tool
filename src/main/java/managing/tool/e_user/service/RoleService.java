package managing.tool.e_user.service;

import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;

public interface RoleService {
    void seedRoles();
    RoleEntity findByName(RoleEnum role);
    boolean rolesAreImported();
}
