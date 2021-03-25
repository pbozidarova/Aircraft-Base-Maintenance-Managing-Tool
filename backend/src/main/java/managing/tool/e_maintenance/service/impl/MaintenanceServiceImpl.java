package managing.tool.e_maintenance.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.MaintenanceStatusEnum;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.repository.MaintenanceRepository;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AircraftService aircraftService;
    private final FacilityService facilityService;

    @Override
    public MaintenanceViewDto updateMaintenance(MaintenanceViewDto maintenanceDataForUpdate, String jwt) {
        MaintenanceEntity maintenanceToUpdate = this.modelMapper.map(maintenanceDataForUpdate, MaintenanceEntity.class);

        MaintenanceEntity maintenanceExisting = this.maintenanceRepository
                .findByMaintenanceNum(maintenanceDataForUpdate.getMaintenanceNum());
        UserEntity responsibleEngineer = this.userService
                .findByCompanyNum(maintenanceDataForUpdate.getResponsibleEngineer().split(" - ")[0]);

        maintenanceToUpdate
                .setAircraft(this.aircraftService.getAircraftByRegistration(maintenanceDataForUpdate.getAircraftRegistration()))
                .setFacility(this.facilityService.getFacilityByName(maintenanceDataForUpdate.getFacility()))
                .setStatus(MaintenanceStatusEnum.valueOf(maintenanceDataForUpdate.getStatus()))
                .setResponsibleEngineer(responsibleEngineer)
                .setId(maintenanceExisting.getId())
                .setUpdatedOn(Instant.now());

        return this.modelMapper.map(this.maintenanceRepository.save(maintenanceToUpdate), MaintenanceViewDto.class);
    }

    @Override
    public MaintenanceViewDto createMaintenance(MaintenanceViewDto maintenanceNew, String jwt) {
        MaintenanceEntity maintenanceToCreate = this.modelMapper.map(maintenanceNew, MaintenanceEntity.class);

        UserEntity responsibleEngineer = this.userService
                .findByCompanyNum(maintenanceNew.getResponsibleEngineer().split(" - ")[0]);

        maintenanceToCreate
                .setAircraft(this.aircraftService.getAircraftByRegistration(maintenanceNew.getAircraftRegistration()))
                .setFacility(this.facilityService.getFacilityByName(maintenanceNew.getFacility()))
                .setStatus(MaintenanceStatusEnum.valueOf(maintenanceNew.getStatus()))
                .setResponsibleEngineer(responsibleEngineer)
                .setCreatedOn(Instant.now());

        return this.modelMapper.map(this.maintenanceRepository.save(maintenanceToCreate), MaintenanceViewDto.class);

    }

    @Override
    public List<MaintenanceViewDto> findAllMaintenanceEvents() {

        return this.maintenanceRepository
                .findAll()
                .stream()
                .map(this::buildMaintenanceVMRelationalStrings)
                .collect(Collectors.toList());
    }

    @Override
    public MaintenanceViewDto findMaintenanceByNum(String maintenanceNum) {
        MaintenanceEntity maintenanceEntity = this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);

        return this.modelMapper.map(maintenanceEntity, MaintenanceViewDto.class);
    }

    @Override
    public List<MaintenanceViewDto> findAllMaintenanceByResponsibleEngineer(String companyNum) {
        UserEntity userEntity = this.userService.findByCompanyNum(companyNum);

        return this.maintenanceRepository.findAllByResponsibleEngineer(userEntity)
                .stream()
                .map(this::buildMaintenanceVMRelationalStrings)
                .collect(Collectors.toList());
    }


    @Override
    public MaintenanceEntity findByMaintenanceNum(String maintenanceNum) {
        return this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);
    }

    @Override
    public Boolean maintenanceExists(String maintenanceNum) {
        return this.findByMaintenanceNum(maintenanceNum) != null;
    }

    private MaintenanceViewDto buildMaintenanceVMRelationalStrings(MaintenanceEntity maintenanceEntity){
        MaintenanceViewDto maintenanceViewModel = this.modelMapper.map(maintenanceEntity, MaintenanceViewDto.class);
        maintenanceViewModel.setFacility(maintenanceEntity.getFacility().getName())
                .setAircraftRegistration(maintenanceEntity.getAircraft().getAircraftRegistration())
                .setResponsibleEngineer(
                        String.format("%s - %s, %s",
                                maintenanceEntity.getResponsibleEngineer().getCompanyNum(),
                                maintenanceEntity.getResponsibleEngineer().getLastName(),
                                maintenanceEntity.getResponsibleEngineer().getFirstName()
                        ));

        return maintenanceViewModel;

    }

}
