package managing.tool.e_facility.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.constants.GlobalConstants;
import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_facility.model.dto.FacilitySeedDto;
import managing.tool.e_facility.model.dto.FacilityViewDto;
import managing.tool.e_facility.repository.FacilityRepository;
import managing.tool.e_facility.service.FacilitySeedService;
import managing.tool.e_facility.service.FacilityService;
import managing.tool.e_task.model.TaskEntity;
import managing.tool.e_user.model.UserEntity;
import managing.tool.e_user.service.UserSeedService;
import managing.tool.e_user.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FacilitySeedServiceImpl implements FacilitySeedService {
    private final FacilityRepository facilityRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Random random;
    private final Gson gson;

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

        this.userService.findAll()
                .forEach(user -> {
                    user.setFacility(this.getRandomFacility());
                    this.userService.saveUser(user);
                });
    }

    @Override
    public boolean areFacilitiesImported() {
        return this.facilityRepository.count() > 0;
    }

    @Override
    public FacilityEntity getRandomFacility() {

        long randomId = random.nextInt((int) this.facilityRepository.count()) + 1;
        FacilityEntity facility = this.facilityRepository.getOne(randomId);

        return facility;
    }


}
