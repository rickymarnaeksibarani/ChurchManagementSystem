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

    @Query("SELECT COALESCE(SUM(i.totalOutcome), 0) " +
            "FROM OutcomeEntity i " +
            "WHERE EXTRACT(YEAR FROM i.outcomeDate) = :year " +
            "AND (:month IS NULL OR EXTRACT(MONTH FROM i.outcomeDate) = :month)")
    BigDecimal getTotalOutcomeByYearAndMonth(@Param("year") int year,
                                            @Param("month") Integer month);

    @Query("SELECT COALESCE(SUM(i.totalOutcome), 0) FROM OutcomeEntity i WHERE EXTRACT(YEAR FROM i.outcomeDate) = :year")
    BigDecimal sumTotalOutcomeByYear(@Param("year") int year);

    @Query("SELECT COALESCE(SUM(i.totalOutcome), 0) FROM OutcomeEntity i WHERE EXTRACT(YEAR FROM i.outcomeDate) = :year AND EXTRACT(MONTH FROM i.outcomeDate) = :month")
    BigDecimal sumTotalOutcomeByYearAndMonth(@Param("year") int year, @Param("month") int month);
}
