package managing.tool.model.entity;

import managing.tool.model.entity.enumeration.Position;
import managing.tool.model.entity.enumeration.Role;

import javax.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String companyId;
    private Role role;
    private Position position;

    public User() {
    }

    public long getId() {
        return id;
    }

    public User setId(long id) {
        this.id = id;
        return this;
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

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCompanyId() {
        return companyId;
    }

    public User setCompanyId(String companyId) {
        this.companyId = companyId;
        return this;
    }

    public Role getRole() {
        return role;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public User setPosition(Position position) {
        this.position = position;
        return this;
    }
}
