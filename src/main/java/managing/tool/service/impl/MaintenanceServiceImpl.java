package managing.tool.service.impl;

import com.google.gson.Gson;
import managing.tool.constants.GlobalConstants;
import managing.tool.model.dto.seed.MaintenanceSeedDto;
import managing.tool.model.entity.*;
import managing.tool.model.entity.enumeration.MaintenanceStatusEnum;
import managing.tool.repository.MaintenanceRepository;
import managing.tool.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final AircraftService aircraftService;
    private final FacilityService facilityService;
    private final UserService userService;
    private final TaskService taskService;

    @Autowired
    public MaintenanceServiceImpl(MaintenanceRepository maintenanceEventRepository, ModelMapper modelMapper, Gson gson, Random random, AircraftService aircraftService, FacilityService facilityService, UserService userService, TaskServiceImpl taskService) {
        this.maintenanceRepository = maintenanceEventRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.aircraftService = aircraftService;
        this.facilityService = facilityService;
        this.userService = userService;
        this.taskService = taskService;
    }


    @Override
    public void seedMaintenance() throws FileNotFoundException {
        if(areEventsImported()){
            return;
        }

        MaintenanceSeedDto[] dtos = this.gson.fromJson(
                new FileReader(GlobalConstants.EVENTS_MOCK_DATA_PATH), MaintenanceSeedDto[].class
        );

        Arrays.stream(dtos)
                .forEach(mDto -> {
                    Maintenance maintenance = this.modelMapper.map(
                            mDto, Maintenance.class
                    );

                    LocalDate startDate = LocalDate.parse(
                            mDto.getStartDate(), DateTimeFormatter.ofPattern("dd/M/yyyy"));

                    Aircraft aircraft = this.aircraftService.getAircraftByRegistration(mDto.getAircraftRegistration());
                    Facility facility = this.facilityService.getFacilityByName(mDto.getFacility());
                    User engineer = this.userService.findByCompanyNum(mDto.getEngineer());
                    Set<Task> randomTasks = this.taskService.getRandomTaskList();

                    maintenance
                            .setStartDate(startDate)
                            .setEndDate(startDate.plusMonths(1))
                            .setStatus(MaintenanceStatusEnum.OPENED)
                            .setAircraft(aircraft)
                            .setFacility(facility)
                            .setEngineer(engineer)
                            .setTasks(new HashSet<>(randomTasks));

                    this.maintenanceRepository.saveAndFlush(maintenance);
                });
    }

    @Override
    public boolean areEventsImported() {

        return this.maintenanceRepository.count() > 0;
    }

}
