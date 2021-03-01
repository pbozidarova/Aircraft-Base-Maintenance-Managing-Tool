package managing.tool.service;

import java.io.FileNotFoundException;

public interface MaintenanceService {
    void seedMaintenance() throws FileNotFoundException;
    boolean areEventsImported();

}
