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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Override
    public FacilityViewDto updateFacility(FacilityViewDto facilityDataForUpdate, String jwt) {
        FacilityEntity facilityToBeUpdated = this.modelMapper.map(facilityDataForUpdate, FacilityEntity.class);
        FacilityEntity facilityExisting = this.facilityRepository.findByName(facilityDataForUpdate.getName());
        String companyNumOfManager = facilityDataForUpdate.getManager().split(" - ")[0];

        facilityToBeUpdated.setManager(this.userService.findByCompanyNum(companyNumOfManager))
                .setEmployees(facilityExisting.getEmployees())
                .setUpdatedOn(Instant.now())
                .setId(facilityExisting.getId());

        return this.modelMapper.map(this.facilityRepository.save(facilityToBeUpdated), FacilityViewDto.class);
    }

    @Override
    public FacilityViewDto createFacility(FacilityViewDto facilityNew, String jwt) {
        FacilityEntity facilityToBeCreated = this.modelMapper.map(facilityNew, FacilityEntity.class);
        String companyNumOfManager = facilityNew.getManager().split(" - ")[0];

        facilityToBeCreated.setManager(this.userService.findByCompanyNum(companyNumOfManager))
                .setCreatedOn(Instant.now());

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
                            .setManager(String.format("%s - %s, %s", f.getManager().getCompanyNum(),
                                                        f.getManager().getLastName(), f.getManager().getFirstName()));
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
