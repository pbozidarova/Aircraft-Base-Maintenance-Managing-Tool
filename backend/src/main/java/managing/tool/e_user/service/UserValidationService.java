package managing.tool.e_user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

public interface UserValidationService {
    void validateIfUserExists(String companyNumOfManager);
    void validateIfRolesExist(String roles);
}
