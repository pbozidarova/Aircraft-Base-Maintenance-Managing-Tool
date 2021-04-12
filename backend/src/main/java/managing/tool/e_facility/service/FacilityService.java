package managing.tool.e_facility.service;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_user.model.dto.UserViewDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface FacilityService {

    FacilityViewDto updateFacility(String name, FacilityViewDto facilityDataForUpdate, String jwt);
    FacilityViewDto createFacility(String name, FacilityViewDto facilityNew, String jwt);

    List<UserViewDto> findAllUsersByFacilityName(String name);

    List<FacilityViewDto> findAll();
    FacilityEntity getFacilityByName(String name);

    Boolean facilityExists(String name);

}
