package managing.tool.e_aircraft.web;

import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.service.AircraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static managing.tool.constants.GlobalConstants.FRONTEND_URL;

@RestController
@CrossOrigin(FRONTEND_URL)
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    @Autowired
    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<AircraftViewDto>> getALl(){
        return ResponseEntity
                    .ok()
                    .body(this.aircraftService.findAll());
    }

}
