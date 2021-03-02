package managing.tool.e_user.model;


import managing.tool.e_base.BaseEntity;
import managing.tool.e_facility.model.FacilityEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private String password;
    private String companyNum;
    private String email;
    private Set<RoleEntity> role;
    private FacilityEntity facility;

    public UserEntity() {
    }

    public UserEntity(String firstName, String lastName, String password, String companyNum, String email, Set<RoleEntity> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.companyNum = companyNum;
        this.email = email;
        this.role = role;
    }

    @Column(name = "first_name", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public UserEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
    @Column(name = "last_name", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public UserEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Column(name = "company_num", nullable = false, unique = true)
    public String getCompanyNum() {
        return companyNum;
    }

    public UserEntity setCompanyNum(String companyId) {
        this.companyNum = companyId;
        return this;
    }
    @Column(name = "email", nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    public Set<RoleEntity> getRole() {
        return role;
    }

    public UserEntity setRole(Set<RoleEntity> role) {
        this.role = role;
        return this;
    }

    @OneToOne(mappedBy = "manager", fetch = FetchType.EAGER)
    public FacilityEntity getFacility() {
        return facility;
    }

    public UserEntity setFacility(FacilityEntity facility) {
        this.facility = facility;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", companyId='").append(companyNum).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity user = (UserEntity) o;
        return companyNum.equals(user.companyNum) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyNum, email);
    }
}
