package managing.tool.repository;

import managing.tool.model.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, Long> {
    Aircraft findByAircraftRegistration(String registration);
}
