package managing.tool.service;

import managing.tool.model.entity.Facility;

import java.io.FileNotFoundException;

public interface FacilityService {
    void seedFacilities() throws FileNotFoundException;
    boolean areFacilitiesImported();

    Facility getFacilityByName(String name);
}
