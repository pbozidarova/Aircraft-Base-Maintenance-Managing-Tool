package managing.tool.e_facility.service;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilityViewDto;

import java.io.FileNotFoundException;
import java.util.List;

public interface FacilityService {

    List<FacilityViewDto> findAll();


    FacilityEntity getFacilityByName(String name);
}
