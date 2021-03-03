package managing.tool.e_facility.repository;

import managing.tool.e_facility.model.FacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Long> {

    FacilityEntity findByName(String name);
}
