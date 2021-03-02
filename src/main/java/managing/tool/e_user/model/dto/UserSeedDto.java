package managing.tool.e_user.model.dto;

import com.google.gson.annotations.Expose;

public class UserSeedDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String email;
    @Expose
    private String companyNum;
    @Expose
    private String role;


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

    public String getCompanyNum() {
        return companyNum;
    }

    public UserSeedDto setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
        return this;
    }

    public String getRole() {
        return role;
    }

    public UserSeedDto setRole(String role) {
        this.role = role;
        return this;
    }
}
