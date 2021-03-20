package managing.tool.e_user.service;

import java.io.FileNotFoundException;

public interface UserSeedService {

    void seedUsers() throws FileNotFoundException;
    boolean userAreImported();


}
