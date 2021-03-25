package managing.tool.e_aircraft.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import managing.tool.e_aircraft.model.dto.AircraftViewDto;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.model.dto.AircraftSeedDto;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.repository.AircraftRepository;
import managing.tool.util.ServiceUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static managing.tool.constants.GlobalConstants.AIRCRAFT_MOCK_DATA_PATH;

@Service
@AllArgsConstructor
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final ModelMapper modelMapper;
    //private final ServiceUtil util;

    @Override
    public AircraftViewDto updateAircraft(AircraftViewDto aircraftDataForUpdate, String jwt) {
        AircraftEntity aircraftNewData = this.modelMapper.map(aircraftDataForUpdate, AircraftEntity.class);

        AircraftEntity aircraftExisting = this.aircraftRepository.findByAircraftRegistration(aircraftDataForUpdate.getAircraftRegistration());

        aircraftNewData.setId(aircraftExisting.getId());
        aircraftNewData.setUpdatedOn(Instant.now());

        return this.modelMapper.map(this.aircraftRepository.save(aircraftNewData), AircraftViewDto.class);
    }

    @Override
    public AircraftViewDto createAircraft(AircraftViewDto aircraftNew, String jwt) {
        AircraftEntity aircraftToCreate = this.modelMapper.map(aircraftNew, AircraftEntity.class);
        aircraftToCreate.setCreatedOn(Instant.now());

        return this.modelMapper.map(this.aircraftRepository.save(aircraftToCreate), AircraftViewDto.class);
    }

    @Override
    public List<AircraftViewDto> findAll() {

        return this.aircraftRepository
                .findAll()
                .stream()
                .map(a -> this.modelMapper.map(a, AircraftViewDto.class))
                .collect(Collectors.toList());
    }


    @Override
    public AircraftEntity getAircraftByRegistration(String registration) {

        return this.aircraftRepository.findByAircraftRegistration(registration);
    }

    @Override
    public Boolean aircraftExists(String registration) {
        return this.getAircraftByRegistration(registration) != null;
    }


}
