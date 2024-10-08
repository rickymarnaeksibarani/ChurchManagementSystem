package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<OutcomeEntity, Long>, JpaSpecificationExecutor<OutcomeRepository> {
    @Query("SELECT i FROM OutcomeEntity i WHERE MONTH(i.outcomeDate) = :month AND YEAR(i.outcomeDate) = :year")
    Page<OutcomeEntity> findByMonth(@Param("month") int month, @Param("year") int year, Pageable pageable);

    @Query("SELECT SUM(i.outcomeDeposit + i.outcomeBuilding + i.outcomeDiakonia + i.outcomeEvent + i.outcomeGuest + i.outcomeOperational + i.outcomeOther) FROM OutcomeEntity i WHERE MONTH(i.outcomeDate) = :month AND YEAR(i.outcomeDate) = :year")
    BigDecimal findTotalOutcomeByMonth(@Param("month") int month, @Param("year") int year);
}
