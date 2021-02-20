package managing.tool.model.dto.seed;

import com.google.gson.annotations.Expose;

import javax.persistence.Column;

public class UserSeedDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;
    @Expose
    private String role;
    @Expose
    private String position;

    public UserSeedDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public UserSeedDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserSeedDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserSeedDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserSeedDto setRole(String role) {
        this.role = role;
        return this;
    }

    public String getPosition() {
        return position;
    }

    public UserSeedDto setPosition(String position) {
        this.position = position;
        return this;
    }
}
