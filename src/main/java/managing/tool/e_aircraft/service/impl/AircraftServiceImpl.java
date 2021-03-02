package managing.tool.e_aircraft.service.impl;

import com.google.gson.Gson;
import managing.tool.e_aircraft.service.AircraftService;
import managing.tool.e_aircraft.model.dto.AircraftSeedDto;
import managing.tool.e_aircraft.model.AircraftEntity;
import managing.tool.e_aircraft.repository.AircraftRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

import static managing.tool.constants.GlobalConstants.AIRCRAFT_MOCK_DATA_PATH;

@Service
public class AircraftServiceImpl implements AircraftService {

    private final AircraftRepository aircraftRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    @Autowired
    public AircraftServiceImpl(AircraftRepository aircraftRepository, Gson gson, ModelMapper modelMapper) {
        this.aircraftRepository = aircraftRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedAircraft() throws FileNotFoundException {
        if(this.aircraftAreImported()){
            return;
        }

        AircraftSeedDto[] dtos =
                this.gson
                        .fromJson(
                            new FileReader(AIRCRAFT_MOCK_DATA_PATH), AircraftSeedDto[].class
                );
        Arrays.stream(dtos)
                .forEach(aDto -> {
                    AircraftEntity aircraft = this.modelMapper.map(aDto, AircraftEntity.class);

                    this.aircraftRepository.save(aircraft);
                });


    }

    @Override
    public boolean aircraftAreImported() {
        return this.aircraftRepository.count() > 0;
    }

    @Override
    public AircraftEntity getAircraftByRegistration(String registration) {

        return this.aircraftRepository.findByAircraftRegistration(registration);
    }


}