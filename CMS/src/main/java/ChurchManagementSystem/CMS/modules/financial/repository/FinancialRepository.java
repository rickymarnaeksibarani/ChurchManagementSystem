package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinancialRepository extends JpaRepository<FinancialEntity, Long>, JpaSpecificationExecutor<FinancialEntity> {
//    Optional<FinancialEntity> findFirst();
//    Optional<FinancialEntity> findFirst();
}
