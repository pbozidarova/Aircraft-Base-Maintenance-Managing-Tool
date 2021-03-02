package managing.tool.e_user.model.dto;

public class UserServiceDto {
    private String companyNum;

    public UserServiceDto() {
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public UserServiceDto setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
        return this;
    }
}
