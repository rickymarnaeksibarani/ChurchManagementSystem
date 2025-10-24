package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long>, JpaSpecificationExecutor<IncomeEntity> {

    List<IncomeEntity>findByIncomeDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COALESCE(SUM(i.totalIncome), 0) FROM IncomeEntity i WHERE EXTRACT(YEAR FROM i.incomeDate) = :year")
    BigDecimal sumTotalIncomeByYear(@Param("year") String year);

    @Query("SELECT COALESCE(SUM(i.totalIncome), 0) FROM IncomeEntity i WHERE EXTRACT(YEAR FROM i.incomeDate) = :year AND EXTRACT(MONTH FROM i.incomeDate) = :month")
    BigDecimal sumTotalIncomeByYearAndMonth(@Param("year") String year, @Param("month") int month);

    @Query("SELECT COALESCE(SUM(i.totalIncome), 0) FROM IncomeEntity i")
    BigDecimal sumTotalIncomeAllTime();

    @Query("SELECT COALESCE(SUM(i.totalIncome), 0) FROM IncomeEntity i WHERE EXTRACT(MONTH FROM i.incomeDate) = :month")
    BigDecimal sumTotalIncomeByMonth(@Param("month") int month);

}
