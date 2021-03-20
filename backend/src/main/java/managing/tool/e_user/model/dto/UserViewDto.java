package managing.tool.e_user.model.dto;

public class UserViewDto {

    private String companyNum;
    private String firstName;
    private String lastName;
    private String email;
    private String facility;
    private String roles;


    public String getFirstName() {
        return firstName;
    }

    public UserViewDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserViewDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public UserViewDto setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserViewDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public UserViewDto setFacility(String facility) {
        this.facility = facility;
        return this;
    }

    public String getRoles() {
        return roles;
    }

    public UserViewDto setRoles(String roles) {
        this.roles = roles;
        return this;
    }
}
