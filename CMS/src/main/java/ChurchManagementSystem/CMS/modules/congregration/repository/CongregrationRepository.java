package ChurchManagementSystem.CMS.modules.congregration.repository;

import ChurchManagementSystem.CMS.modules.congregration.entities.CongregrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CongregrationRepository extends JpaRepository<CongregrationEntity, Long>, JpaSpecificationExecutor<CongregrationEntity> {
}
