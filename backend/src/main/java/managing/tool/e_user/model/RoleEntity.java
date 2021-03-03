package managing.tool.e_user.model;

import managing.tool.e_base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    private RoleEnum role;

    public RoleEntity() {
    }

    public RoleEntity(RoleEnum role) {
        this.role = role;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, unique = true)
    public RoleEnum getName() {
        return role;
    }

    public RoleEntity setName(RoleEnum role) {
        this.role = role;
        return this;

    }
}
