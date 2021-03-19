package managing.tool.e_facility.service.impl;

import com.google.gson.Gson;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilitySeedDto;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_facility.service.FacilitySeedService;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilitySeedServiceImpl implements FacilitySeedService {
    private final FacilityRepository facilityRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public FacilitySeedServiceImpl(FacilityRepository facilityRepository, UserService userService, ModelMapper modelMapper, Gson gson) {
        this.facilityRepository = facilityRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedFacilities() throws FileNotFoundException {
        if(areFacilitiesImported()){
            return;
        }

        FacilitySeedDto[] dtos = this.gson.fromJson(
                new FileReader(GlobalConstants.FACILITIES_MOCK_DATA_PATH), FacilitySeedDto[].class
        );
        System.out.println();
        Arrays.stream(dtos)
                .forEach(fDto -> {
                    FacilityEntity facility = this.modelMapper.map(fDto, FacilityEntity.class);
                    UserEntity user = this.userService.findByCompanyNum(fDto.getManager());
                    facility.setManager(user);
                    //TODO Competences
                    this.facilityRepository.save(facility);
                });
    }

    @Override
    public boolean areFacilitiesImported() {
        return this.facilityRepository.count() > 0;
    }



}
