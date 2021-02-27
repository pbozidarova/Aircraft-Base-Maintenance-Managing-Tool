package managing.tool.service;

import managing.tool.model.entity.Maintenance;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface MaintenanceService {
    void seedMaintenance() throws FileNotFoundException;
    boolean areEventsImported();

}
