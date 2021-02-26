package managing.tool.service;

import java.io.FileNotFoundException;

public interface MaintenanceEventService {

    void seedEvents() throws FileNotFoundException;
    boolean areEventsImported();
}
