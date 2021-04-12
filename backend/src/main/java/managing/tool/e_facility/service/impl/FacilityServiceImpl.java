package managing.tool.e_facility.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.dto.FacilitySeedDto;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_user.model.dto.UserViewDto;
import managing.tool.e_user.repository.UserRepository;
import managing.tool.e_user.service.UserService;
import managing.tool.exception.FoundInDb;
import managing.tool.exception.NotFoundInDb;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.*;

@Service
@AllArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final ServiceUtil serviceUtil;

    @Override
    public FacilityViewDto updateFacility(String name, FacilityViewDto facilityDataForUpdate, String jwt) {
        if(!this.facilityExists(name)){
            throw new NotFoundInDb(String.format(NOTFOUNDERROR, name), "name");
        }

        String companyNumOfManager = this.serviceUtil.companyNumFromUserString(facilityDataForUpdate.getManager());
        validateIfManagerExist(companyNumOfManager);

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
        String companyNumOfManager = this.serviceUtil.companyNumFromUserString(facilityNew.getManager());
        validateIfManagerExist(companyNumOfManager);

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

    private void validateIfManagerExist(String companyNumOfManager) {
        if(!this.userService.userExists(companyNumOfManager)){
            throw new NotFoundInDb(
                    String.format(NOTFOUND_SELECT_ERROR, companyNumOfManager, "employee from the manager's list!"),
                    "companyNumOfManager"
            );
        }
    }


}
