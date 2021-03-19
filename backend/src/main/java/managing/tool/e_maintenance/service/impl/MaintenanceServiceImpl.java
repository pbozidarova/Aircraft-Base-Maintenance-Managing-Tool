package managing.tool.e_maintenance.service.impl;

import com.google.gson.Gson;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.dto.MaintenanceSeedDto;
import managing.tool.e_maintenance.model.MaintenanceStatusEnum;
import managing.tool.e_maintenance.model.dto.MaintenanceViewModel;
import managing.tool.e_maintenance.repository.MaintenanceRepository;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final AircraftService aircraftService;
    private final FacilityService facilityService;
    private final UserService userService;
//    private final TaskService taskService;

    @Autowired
    public MaintenanceServiceImpl(MaintenanceRepository maintenanceEventRepository, ModelMapper modelMapper, Gson gson, Random random, AircraftService aircraftService, FacilityService facilityService, UserService userService) {
        this.maintenanceRepository = maintenanceEventRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.aircraftService = aircraftService;
        this.facilityService = facilityService;
        this.userService = userService;
//        this.taskService = taskService;
    }


    @Override
    public List<MaintenanceViewModel> findAllMaintenanceEvents() {

        return this.maintenanceRepository
                .findAll()
                .stream()
                .map(m -> this.modelMapper.map(m, MaintenanceViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceViewModel findMaintenanceByNum(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);

        return this.modelMapper.map(maintenanceEntity, MaintenanceViewModel.class);
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
                    MaintenanceEntity maintenance = this.modelMapper.map(
                            mDto, MaintenanceEntity.class
                    );

                    LocalDate startDate = LocalDate.parse(
                            mDto.getStartDate(), DateTimeFormatter.ofPattern("dd/M/yyyy"));

                    AircraftEntity aircraft = this.aircraftService.getAircraftByRegistration(mDto.getAircraftRegistration());
                    FacilityEntity facility = this.facilityService.getFacilityByName(mDto.getFacility());
                    UserEntity engineer = this.userService.findByCompanyNum(mDto.getEngineer());
//                    Set<TaskEntity> randomTasks = this.taskService.getRandomTaskList();

                    maintenance
                            .setStartDate(startDate)
                            .setEndDate(startDate.plusMonths(1))
                            .setStatus(MaintenanceStatusEnum.OPENED)
                            .setAircraft(aircraft)
                            .setFacility(facility)
                            .setResponsibleEngineer(engineer);
//                            .setTasks(new HashSet<>(randomTasks));

                    this.maintenanceRepository.saveAndFlush(maintenance);
                });
    }

    @Override
    public boolean areEventsImported() {

        return this.maintenanceRepository.count() > 0;
    }

    @Override
    public MaintenanceEntity findByMaintenanceNum(String maintenanceNum) {

        return this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);
    }

}
