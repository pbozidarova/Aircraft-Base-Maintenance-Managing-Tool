package managing.tool.service;

import managing.tool.model.entity.Role;
import managing.tool.model.entity.enumeration.RoleEnum;

public interface RoleService {
    void seedRoles();
    Role findByName(RoleEnum role);
    boolean rolesAreImported();
}
