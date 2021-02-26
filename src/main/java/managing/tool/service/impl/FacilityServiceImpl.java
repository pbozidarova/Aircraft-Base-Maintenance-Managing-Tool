package managing.tool.service.impl;

import com.google.gson.Gson;
import managing.tool.constants.GlobalConstants;
import managing.tool.model.dto.seed.FacilitySeedDto;
import managing.tool.model.entity.Competence;
import managing.tool.model.entity.Facility;
import managing.tool.model.entity.User;
import managing.tool.repository.FacilityRepository;
import managing.tool.service.FacilityService;
import managing.tool.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

@Service
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public FacilityServiceImpl(FacilityRepository facilityRepository, UserService userService, ModelMapper modelMapper, Gson gson) {
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
                    Facility facility = this.modelMapper.map(fDto, Facility.class);
                    User user = this.userService.findByCompanyNum(fDto.getManager());
                    facility.setManager(user);
                    //TODO Competences
                    this.facilityRepository.save(facility);
                });
    }

    @Override
    public boolean areFacilitiesImported() {
        return this.facilityRepository.count() > 0;
    }

    @Override
    public Facility getFacilityByName(String name) {

        return this.facilityRepository.findByName(name);
    }


}
