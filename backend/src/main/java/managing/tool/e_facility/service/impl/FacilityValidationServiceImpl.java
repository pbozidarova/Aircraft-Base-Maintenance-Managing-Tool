package managing.tool.e_facility.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.service.FacilityValidationService;
import managing.tool.exception.NotFoundInDb;
import org.springframework.stereotype.Service;

import static managing.tool.constants.GlobalConstants.NOTFOUND_SELECT_ERROR;

@Service
@AllArgsConstructor
public class FacilityValidationServiceImpl implements FacilityValidationService {
    private final FacilityService facilityService;

    @Override
    public void validateIfFacilityExists(String facility) {
        if(!this.facilityService.facilityExists(facility)){
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, facility, "facility"),
                    "facility"
            );
        }
    }
}
