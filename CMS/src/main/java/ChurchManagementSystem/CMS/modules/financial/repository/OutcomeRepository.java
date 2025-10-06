package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<OutcomeEntity, Long>, JpaSpecificationExecutor<OutcomeEntity> {

    List<OutcomeEntity>findByOutcomeDateBetween(LocalDateTime start, LocalDateTime end);

}
