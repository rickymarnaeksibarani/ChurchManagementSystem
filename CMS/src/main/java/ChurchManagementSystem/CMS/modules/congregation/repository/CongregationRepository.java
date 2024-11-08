package ChurchManagementSystem.CMS.modules.congregation.repository;

import ChurchManagementSystem.CMS.modules.congregation.entities.CongregationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CongregationRepository extends JpaRepository<CongregationEntity, Long>, JpaSpecificationExecutor<CongregationEntity> {
}
