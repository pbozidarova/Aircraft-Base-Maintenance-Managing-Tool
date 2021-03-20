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
@AllArgsConstructor
public class FacilityServiceImpl implements FacilityService {
    private final FacilityRepository facilityRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final Gson gson;


    @Override
    public List<FacilityViewDto> findAll() {

        return this.facilityRepository
                .findAll()
                .stream()
                .map(f -> this.modelMapper.map(f, FacilityViewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public FacilityEntity getFacilityByName(String name) {

        return this.facilityRepository.findByName(name);
    }


}
