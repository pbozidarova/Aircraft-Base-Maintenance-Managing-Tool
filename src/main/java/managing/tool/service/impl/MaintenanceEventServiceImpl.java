package managing.tool.service.impl;

import com.google.gson.Gson;
import managing.tool.constants.GlobalConstants;
import managing.tool.model.dto.seed.MaintenanceEventSeedDto;
import managing.tool.model.entity.Aircraft;
import managing.tool.model.entity.Facility;
import managing.tool.model.entity.MaintenanceEvent;
import managing.tool.model.entity.User;
import managing.tool.model.entity.enumeration.EventStatusEnum;
import managing.tool.repository.MaintenanceEventRepository;
import managing.tool.service.AircraftService;
import managing.tool.service.FacilityService;
import managing.tool.service.MaintenanceEventService;
import managing.tool.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Service
public class MaintenanceEventServiceImpl implements MaintenanceEventService {
    private final MaintenanceEventRepository maintenanceEventRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final AircraftService aircraftService;
    private final FacilityService facilityService;
    private final UserService userService;

    @Autowired
    public MaintenanceEventServiceImpl(MaintenanceEventRepository maintenanceEventRepository, ModelMapper modelMapper, Gson gson, AircraftService aircraftService, FacilityService facilityService, UserService userService) {
        this.maintenanceEventRepository = maintenanceEventRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.aircraftService = aircraftService;
        this.facilityService = facilityService;
        this.userService = userService;
    }


    @Override
    public void seedEvents() throws FileNotFoundException {
        if(areEventsImported()){
            return;
        }

        MaintenanceEventSeedDto[] dtos = this.gson.fromJson(
                new FileReader(GlobalConstants.EVENTS_MOCK_DATA_PATH), MaintenanceEventSeedDto[].class
        );

        Arrays.stream(dtos)
                .forEach(mDto -> {
                    MaintenanceEvent maintenanceEvent = this.modelMapper.map(
                            mDto, MaintenanceEvent.class
                    );

                    LocalDate startDate = LocalDate.parse(
                            mDto.getStartDate(), DateTimeFormatter.ofPattern("dd/M/yyyy"));
                    Aircraft aircraft = this.aircraftService.getAircraftByRegistration(mDto.getAircraftRegistration());
                    Facility facility = this.facilityService.getFacilityByName(mDto.getFacility());
                    User engineer = this.userService.findByCompanyNum(mDto.getEngineer());

                    maintenanceEvent
                            .setStartDate(startDate)
                            .setEndDate(startDate.plusMonths(1))
                            .setStatus(EventStatusEnum.OPENED)
                            .setAircraft(aircraft)
                            .setFacility(facility)
                            .setEngineer(engineer);

                    this.maintenanceEventRepository.save(maintenanceEvent);
                });
    }

    @Override
    public boolean areEventsImported() {

        return this.maintenanceEventRepository.count() > 0;
    }
}
