package managing.tool.e_user.model.dto;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_user.model.RoleEntity;

import java.util.Set;

public class UserSingleViewDto {

    private String firstName;
    private String lastName;
    private String password;
    private String companyNum;
    private String email;
    private String facility;

    private Boolean ADMIN;
    private Boolean USER;
    private Boolean MECHANIC;
    private Boolean ENGINEER;

    public String getFirstName() {
        return firstName;
    }

    public UserSingleViewDto setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserSingleViewDto setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserSingleViewDto setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public UserSingleViewDto setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserSingleViewDto setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getFacility() {
        return facility;
    }

    public UserSingleViewDto setFacility(String facility) {
        this.facility = facility;
        return this;
    }

    public Boolean getADMIN() {
        return ADMIN;
    }

    public UserSingleViewDto setADMIN(Boolean ADMIN) {
        this.ADMIN = ADMIN;
        return this;
    }

    public Boolean getUSER() {
        return USER;
    }

    public UserSingleViewDto setUSER(Boolean USER) {
        this.USER = USER;
        return this;
    }

    public Boolean getMECHANIC() {
        return MECHANIC;
    }

    public UserSingleViewDto setMECHANIC(Boolean MECHANIC) {
        this.MECHANIC = MECHANIC;
        return this;
    }

    public Boolean getENGINEER() {
        return ENGINEER;
    }

    public UserSingleViewDto setENGINEER(Boolean ENGINEER) {
        this.ENGINEER = ENGINEER;
        return this;
    }
}
