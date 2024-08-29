package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
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
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long>, JpaSpecificationExecutor<IncomeEntity> {
    @Query("SELECT i FROM IncomeEntity i WHERE MONTH(i.incomeDate) = :month AND YEAR(i.incomeDate) = :year")
    Page<IncomeEntity> findByMonth(@Param("month") int month, @Param("year") int year, Pageable pageable);

    @Query("SELECT SUM(i.incomeGive + i.incomeTenth + i.incomeBuilding + i.incomeService + i.incomeDonate + i.incomeOther) FROM IncomeEntity i WHERE MONTH(i.incomeDate) = :month AND YEAR(i.incomeDate) = :year")
    BigDecimal findTotalIncomeByMonth(@Param("month") int month, @Param("year") int year);
}
