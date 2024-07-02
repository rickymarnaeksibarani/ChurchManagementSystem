package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface OutcomeRepository extends JpaRepository<OutcomeEntity, Long>, JpaSpecificationExecutor<OutcomeRepository> {
}
