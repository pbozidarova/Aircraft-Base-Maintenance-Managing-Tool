package managing.tool.e_maintenance.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_maintenance.model.MaintenanceStatusEnum;
import managing.tool.e_maintenance.model.dto.MaintenanceViewDto;
import managing.tool.e_maintenance.repository.MaintenanceRepository;
import managing.tool.e_maintenance.service.MaintenanceService;
import managing.tool.e_notification.service.impl.NotificationServiceImpl;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_task.service.TaskSeedService;
import managing.tool.e_task.service.TaskService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@AllArgsConstructor
public class MaintenanceServiceImpl implements MaintenanceService {
    private final MaintenanceRepository maintenanceRepository;
    private final TaskSeedService taskService;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AircraftService aircraftService;
    private final FacilityService facilityService;
    private final ServiceUtil serviceUtil;
    private final Random random;

    private static Logger LOGGER = LoggerFactory.getLogger(MaintenanceServiceImpl.class);


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
                .setStatus(allocateCorrectMaintenanceStatus(maintenanceDataForUpdate.getStartDate(), maintenanceToUpdate.getEndDate()))
                .setResponsibleEngineer(responsibleEngineer)
                .setId(maintenanceExisting.getId())
                .setUpdatedOn(LocalDateTime.now());

        return this.modelMapper.map(this.maintenanceRepository.save(maintenanceToUpdate), MaintenanceViewDto.class);
    }

    @Transactional
    @Override
    public MaintenanceViewDto createMaintenance(MaintenanceViewDto maintenanceNew, String jwt) {
        MaintenanceEntity maintenanceToCreate = this.modelMapper.map(maintenanceNew, MaintenanceEntity.class);

        UserEntity responsibleEngineer = this.userService
                .findByCompanyNum(maintenanceNew.getResponsibleEngineer().split(" - ")[0]);

        maintenanceToCreate
                .setAircraft(this.aircraftService.getAircraftByRegistration(maintenanceNew.getAircraftRegistration()))
                .setFacility(this.facilityService.getFacilityByName(maintenanceNew.getFacility()))
                .setStatus(allocateCorrectMaintenanceStatus(maintenanceNew.getStartDate(), maintenanceNew.getEndDate()))
                .setResponsibleEngineer(responsibleEngineer)
                .setCreatedOn(LocalDateTime.now());

        return this.modelMapper.map(this.maintenanceRepository.save(maintenanceToCreate), MaintenanceViewDto.class);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public void allocateRandomTasksToMaintenance(String maintenanceNum) {
        Set<TaskEntity> randomTasks = this.taskService.getRandomTaskList();
        MaintenanceEntity maintenance = this.findByMaintenanceNum(maintenanceNum);

        maintenance.setTasks(new HashSet<>(randomTasks));
        this.maintenanceRepository.saveAndFlush(maintenance);
    }


    private MaintenanceStatusEnum allocateCorrectMaintenanceStatus(LocalDate startDate, LocalDate endDate){
        MaintenanceStatusEnum status = MaintenanceStatusEnum.UPCOMING;
        if (LocalDate.now().isAfter(startDate)) status = MaintenanceStatusEnum.OPENED;
        if (LocalDate.now().isAfter(endDate)) status = MaintenanceStatusEnum.CLOSED;

        return status;
    }

    @Cacheable("maintenance")
    @Override
    public List<MaintenanceViewDto> findAllMaintenanceEvents() {

        return this.maintenanceRepository
                .findAll()
                .stream()
                .map(this::buildMaintenanceVMRelationalStrings)
                .collect(Collectors.toList());
    }

    @CacheEvict(cacheNames = "maintenance", allEntries = true)
    public void evictCachedMaintenance() {

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
    public List<MaintenanceViewDto> findAllByAircraft(String registration) {

        return this.maintenanceRepository
                .findAllByAircraft_AircraftRegistration(registration)
                .stream().map(this::buildMaintenanceVMRelationalStrings)
                .collect(Collectors.toList());
    }


    @Override
    public MaintenanceEntity findByMaintenanceNum(String maintenanceNum) {
        return this.maintenanceRepository.findByMaintenanceNum(maintenanceNum);
    }

    @Override
    public MaintenanceEntity getRandomMaintenance() {
        long maxRandomNumber = this.maintenanceRepository.count();
        long randomId = this.random.nextInt((int) maxRandomNumber) +1;

        return this.maintenanceRepository.getOne(randomId);
    }

    @Override
    public Boolean maintenanceExists(String maintenanceNum) {
        return this.findByMaintenanceNum(maintenanceNum) != null;
    }

    @Override
    public List<MaintenanceViewDto> findAllByFacility(String name) {
        FacilityEntity facilityEntity = this.facilityService.getFacilityByName(name);

        return this.maintenanceRepository
                .findAllByFacility(facilityEntity)
                .stream()
                .map(this::buildMaintenanceVMRelationalStrings)
                .collect(Collectors.toList());
    }

    private MaintenanceViewDto buildMaintenanceVMRelationalStrings(MaintenanceEntity maintenanceEntity){
        MaintenanceViewDto maintenanceViewModel = this.modelMapper.map(maintenanceEntity, MaintenanceViewDto.class);

        maintenanceViewModel.setFacility(maintenanceEntity.getFacility().getName())
                .setAircraftRegistration(maintenanceEntity.getAircraft().getAircraftRegistration())
                .setResponsibleEngineer(this.serviceUtil.userViewStringBuild(maintenanceEntity.getResponsibleEngineer()));

        return maintenanceViewModel;
    }

    @Override
    @Scheduled(cron = "0 01 */1 * * *")
//    @Scheduled(cron = "*/2 * * * * *") //for testing
    public void recalculateMaintenanceStatus(){
        this.maintenanceRepository
                .findAll()
                .forEach(maintenance -> {
                    LocalDate startDate = maintenance.getStartDate();
                    LocalDate endDate = maintenance.getEndDate();

                    boolean startDateHasPassed = startDate.isBefore(LocalDate.now());
                    boolean endDateHasPassed = endDate.isBefore(LocalDate.now());

                    //Start date is later than today
                    if(!startDateHasPassed) {
                        maintenance.setStatus(MaintenanceStatusEnum.UPCOMING);
                    }else if(startDateHasPassed && !endDateHasPassed){
                        maintenance.setStatus(MaintenanceStatusEnum.OPENED);
                    }else if(startDateHasPassed && endDateHasPassed){
                        maintenance.setStatus((MaintenanceStatusEnum.CLOSED));
                    }
                });

        LOGGER.info("The maintenance statuses have been recalculated!");
    }

}
