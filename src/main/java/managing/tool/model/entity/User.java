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
    private String companyId;
    private String email;
    private Set<Role> role;


    public User() {
    }

    public User(String firstName, String lastName, String companyId, String email, Set<Role> role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyId = companyId;
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

    public String getCompanyId() {
        return companyId;
    }

    public User setCompanyId(String username) {
        this.companyId = username;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", companyId='").append(companyId).append('\'');
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
        return companyId.equals(user.companyId) && email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, email);
    }
}
