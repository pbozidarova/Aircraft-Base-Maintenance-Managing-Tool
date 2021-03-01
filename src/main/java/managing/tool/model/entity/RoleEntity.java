package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.RoleEnum;

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
