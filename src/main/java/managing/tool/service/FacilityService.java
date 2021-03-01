package managing.tool.service;

import managing.tool.model.entity.FacilityEntity;

import java.io.FileNotFoundException;

public interface FacilityService {
    void seedFacilities() throws FileNotFoundException;
    boolean areFacilitiesImported();

    FacilityEntity getFacilityByName(String name);
}
