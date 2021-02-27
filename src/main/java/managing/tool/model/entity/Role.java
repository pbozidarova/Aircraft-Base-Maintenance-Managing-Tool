package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.RoleEnum;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity {

    private RoleEnum role;

    public Role() {
    }

    public Role(RoleEnum role) {
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    public RoleEnum getName() {
        return role;
    }

    public Role setName(RoleEnum role) {
        this.role = role;
        return this;

    }
}
