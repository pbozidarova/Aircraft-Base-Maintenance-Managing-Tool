package managing.tool.e_facility.service;

import managing.tool.e_facility.model.FacilityEntity;

import java.io.FileNotFoundException;

public interface FacilityService {
    void seedFacilities() throws FileNotFoundException;
    boolean areFacilitiesImported();

    FacilityEntity getFacilityByName(String name);
}
