package managing.tool.service;

import java.io.FileNotFoundException;

public interface FacilityService {
    void seedFacilities() throws FileNotFoundException;
    boolean areFacilitiesImported();
}
