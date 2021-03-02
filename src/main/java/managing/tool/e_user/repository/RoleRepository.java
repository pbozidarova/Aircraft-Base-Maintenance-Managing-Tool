package managing.tool.e_user.repository;

import managing.tool.e_user.model.RoleEntity;
import managing.tool.e_user.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(RoleEnum role);
}
