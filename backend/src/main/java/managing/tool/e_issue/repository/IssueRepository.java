package managing.tool.e_issue.repository;

import managing.tool.e_issue.model.IssueEntity;
import managing.tool.e_maintenance.model.MaintenanceEntity;
import managing.tool.e_user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {

    List<IssueEntity> findAllByAuthor(UserEntity userEntity);
    List<IssueEntity> findAllByMaintenance(MaintenanceEntity maintenanceEntity);
}
