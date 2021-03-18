package managing.tool.e_user.model.dto;

public class UserAllViewDto {

    private String companyNum;
    private String names;
    private String email;
    private String roles;

    public UserAllViewDto() {
    }

    public String getNames() {
        return names;
    }

    public UserAllViewDto setNames(String names) {
        this.names = names;
        return this;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public UserAllViewDto setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAllViewDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public UserAllViewDto setRoles(String roles) {
        this.roles = roles;
        return this;
    }
}
