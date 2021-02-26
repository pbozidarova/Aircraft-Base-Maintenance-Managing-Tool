package managing.tool.service;

import managing.tool.model.dto.service.UserServiceDto;
import managing.tool.model.entity.User;

import java.io.FileNotFoundException;

public interface UserService {

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();

    User findByCompanyNum(String companyNum);
}
