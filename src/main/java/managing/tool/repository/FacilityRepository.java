package managing.tool.repository;

import managing.tool.model.entity.FacilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<FacilityEntity, Long> {

    FacilityEntity findByName(String name);
}
