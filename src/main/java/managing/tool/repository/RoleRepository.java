package managing.tool.repository;

import managing.tool.model.entity.RoleEntity;
import managing.tool.model.entity.enumeration.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(RoleEnum role);
}
