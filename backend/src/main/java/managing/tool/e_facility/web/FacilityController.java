package managing.tool.e_facility.web;

import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(GlobalConstants.FRONTEND_URL)
@RequestMapping("/facilities")
public class FacilityController {

    private final FacilityService facilityService;

    @Autowired
    public FacilityController(FacilityService facilityService) {
        this.facilityService = facilityService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FacilityViewDto>> getAll(){

        return  ResponseEntity
                .ok()
                .body(this.facilityService.findAll());

    }

}
