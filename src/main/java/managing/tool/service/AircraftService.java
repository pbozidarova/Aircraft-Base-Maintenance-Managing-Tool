package managing.tool.service;

import java.io.FileNotFoundException;

public interface AircraftService {
    void seedAircraft() throws FileNotFoundException;
    boolean aircraftAreImported();
}
