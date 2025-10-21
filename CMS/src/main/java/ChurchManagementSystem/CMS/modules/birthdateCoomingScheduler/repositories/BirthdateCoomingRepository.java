package ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.repositories;

import ChurchManagementSystem.CMS.modules.birthdateCoomingScheduler.entities.BirthdateCoomingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirthdateCoomingRepository extends JpaRepository<BirthdateCoomingEntity, Long> {
}
