package managing.tool.model.entity;


import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String password;
    private String companyNum;
    private String email;
    private Set<Role> role;
    private Facility facility;

    public User() {
    }

    public User(String firstName, String lastName, String password, String companyNum, String email, Set<Role> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.companyNum = companyNum;
        this.email = email;
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public User setCompanyNum(String companyId) {
        this.companyNum = companyId;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }


    @ManyToMany(cascade = CascadeType.MERGE)
    public Set<Role> getRole() {
        return role;
    }

    public User setRole(Set<Role> role) {
        this.role = role;
        return this;
    }

    @OneToOne(mappedBy = "manager")
    public Facility getFacility() {
        return facility;
    }

    public User setFacility(Facility facility) {
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
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return companyNum.equals(user.companyNum) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyNum, email);
    }
}
