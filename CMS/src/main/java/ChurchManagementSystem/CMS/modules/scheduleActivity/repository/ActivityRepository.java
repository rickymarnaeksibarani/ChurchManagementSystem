package ChurchManagementSystem.CMS.modules.scheduleActivity.repository;

import ChurchManagementSystem.CMS.modules.scheduleActivity.entities.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long>, JpaSpecificationExecutor<ActivityEntity> {
    boolean existsByActivityTitle(String activityName);
}
