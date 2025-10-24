package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.repositories;

import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.entities.BirthdateComingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdateComingRepository extends JpaRepository<BirthdateComingEntity, Long> {
}
