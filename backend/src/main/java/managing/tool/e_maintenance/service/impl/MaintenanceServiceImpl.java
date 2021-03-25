package managing.tool.e_maintenance.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public List<MaintenanceViewModel> findAllMaintenanceEvents() {

        return this.maintenanceRepository
                .findAll()
                .stream()
                .map(m -> {
                    MaintenanceViewModel maintenanceViewModel = this.modelMapper.map(m, MaintenanceViewModel.class);
                    maintenanceViewModel.setFacility(m.getFacility().getName())
                                        .setAircraftRegistration(m.getAircraft().getAircraftRegistration())
                                        .setResponsibleEngineer(
                                                String.format("%s - %s, %s",
                                                        m.getResponsibleEngineer().getCompanyNum(),
                                                        m.getResponsibleEngineer().getFirstName(),
                                                        m.getResponsibleEngineer().getLastName()
                                        ));

                    return maintenanceViewModel;
                })
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceViewModel findMaintenanceByNum(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);

        return this.modelMapper.map(maintenanceEntity, MaintenanceViewModel.class);
    }

    @Override
    public List<MaintenanceViewModel> findAllMaintenanceByResponsibleEngineer(String companyNum) {
        UserEntity userEntity = this.userService.findByCompanyNum(companyNum);

        return this.maintenanceRepository.findAllByResponsibleEngineer(userEntity)
                .stream()
                .map(m -> this.modelMapper.map(m, MaintenanceViewModel.class))
                .collect(Collectors.toList());
    }



    @Override
    public MaintenanceEntity findByMaintenanceNum(String maintenanceNum) {

        return this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);
    }

}
