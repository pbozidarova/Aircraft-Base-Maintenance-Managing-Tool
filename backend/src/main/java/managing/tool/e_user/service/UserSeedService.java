package managing.tool.e_user.service;

import managing.tool.e_user.model.UserEntity;

import java.io.FileNotFoundException;
import java.util.List;

public interface UserSeedService {

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();



}
