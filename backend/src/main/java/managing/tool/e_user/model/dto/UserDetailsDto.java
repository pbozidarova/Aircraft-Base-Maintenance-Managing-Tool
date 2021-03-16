package managing.tool.e_user.model.dto;

import managing.tool.e_user.model.RoleEntity;

import java.util.Set;

public class UserDetailsDto {

    private String companyNum;
    private String password;
    private Set<RoleEntity> roles;

    public UserDetailsDto() {
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public UserDetailsDto setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDetailsDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public UserDetailsDto setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
        return this;
    }
}
