package managing.tool.e_user.model.dto;

public class UserViewDto {

    private String firstName;
    private String lastName;
    private String companyNum;
    private String email;

    public UserViewDto() {
    }


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
}
