package managing.tool.e_user.repository;

import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByCompanyNum(String num);
//    Optional<UserEntity> findByCompanyNum(String num);
}
