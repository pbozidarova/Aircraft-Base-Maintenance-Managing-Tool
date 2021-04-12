package managing.tool.e_facility.service.impl;

import lombok.AllArgsConstructor;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.service.UserService;
import managing.tool.e_user.service.UserValidationService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.*;

@Service
@AllArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final UserValidationService userValidationService;
    private final ServiceUtil serviceUtil;

    @Override
    public FacilityViewDto updateFacility(String name, FacilityViewDto facilityDataForUpdate, String jwt) {
        if(!this.facilityExists(name)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, name), "name");
        }

        String companyNumOfManager = this.serviceUtil.companyNumFromUserString(facilityDataForUpdate.getManager());
        userValidationService.validateIfUserExists(companyNumOfManager);

        FacilityEntity facilityToBeUpdated = this.modelMapper.map(facilityDataForUpdate, FacilityEntity.class);
        FacilityEntity facilityExisting = this.facilityRepository.findByName(facilityDataForUpdate.getName());

        facilityToBeUpdated.setManager(this.userService.findByCompanyNum(companyNumOfManager))
                .setEmployees(facilityExisting.getEmployees())
                .setUpdatedOn(LocalDateTime.now())
                .setId(facilityExisting.getId());

        return this.modelMapper.map(this.facilityRepository.save(facilityToBeUpdated), FacilityViewDto.class);
    }


    @Override
    public FacilityViewDto createFacility(String name, FacilityViewDto facilityNew, String jwt) {
        if(this.facilityExists(name)){
            throw new FoundInDb(String.format(FOUNDERROR, name), "name");
        }
        //VALIDATE SELECT FIELD
        String companyNumOfManager = this.serviceUtil.companyNumFromUserString(facilityNew.getManager());
        userValidationService.validateIfUserExists(companyNumOfManager);

        FacilityEntity facilityToBeCreated = this.modelMapper.map(facilityNew, FacilityEntity.class);

        facilityToBeCreated.setManager(this.userService.findByCompanyNum(companyNumOfManager))
                           .setCreatedOn(LocalDateTime.now());

        return this.modelMapper.map(this.facilityRepository.save(facilityToBeCreated), FacilityViewDto.class);
    }

    @Override
    public List<UserViewDto> findAllUsersByFacilityName(String name) {

        return this.facilityRepository.findByName(name)
                .getEmployees()
                .stream()
                .map(employee -> {
                    UserViewDto userView = this.modelMapper.map(employee, UserViewDto.class);

                    userView.setFacility(employee.getFacility().getName());

                    userView.setRoles( userView
                            .getRoles()
                            .replace("[", "")
                            .replace("]", "")
                    );
                    System.out.println();
                    return userView;
                })
                .collect(Collectors.toList());

    }

    @Override
    public List<FacilityViewDto> findAll() {
        return this.facilityRepository
                .findAll()
                .stream()
                .map(f -> {
                    FacilityViewDto facilityViewDto = this.modelMapper.map(f, FacilityViewDto.class);
                    facilityViewDto
                            .setManager(this.serviceUtil.userViewStringBuild(f.getManager()));

                    return facilityViewDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FacilityEntity getFacilityByName(String name) {
        return this.facilityRepository.findByName(name);
    }

    @Override
    public Boolean facilityExists(String name) {
        return this.facilityRepository.findByName(name) != null ;
    }



}
