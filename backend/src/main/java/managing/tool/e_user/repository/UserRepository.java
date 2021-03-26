package managing.tool.e_user.repository;

import managing.tool.e_facility.model.FacilityEntity;
import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByCompanyNum(String num);
    UserEntity findByEmail(String email);
    List<UserEntity> findAllByFacility(FacilityEntity facilityEntity);
//    Optional<UserEntity> findByCompanyNum(String num);
}
