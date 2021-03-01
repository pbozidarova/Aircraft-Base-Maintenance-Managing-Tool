package managing.tool.repository;

import managing.tool.model.entity.AircraftEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<AircraftEntity, Long> {
    AircraftEntity findByAircraftRegistration(String registration);
}
