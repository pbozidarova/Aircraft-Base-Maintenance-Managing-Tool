package managing.tool.e_maintenance.service;

import java.io.FileNotFoundException;

public interface MaintenanceSeedService {

    void seedMaintenance() throws FileNotFoundException;
    boolean areEventsImported();

}

