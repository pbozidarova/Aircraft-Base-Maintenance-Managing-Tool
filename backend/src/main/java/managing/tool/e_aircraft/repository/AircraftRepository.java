package managing.tool.e_aircraft.repository;

import managing.tool.e_aircraft.model.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {
    AircraftEntity findByAircraftRegistration(String registration);
}
