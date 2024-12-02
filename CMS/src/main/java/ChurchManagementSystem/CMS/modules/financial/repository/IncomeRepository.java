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

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long>, JpaSpecificationExecutor<IncomeEntity> {
    @Query("SELECT i FROM IncomeEntity i WHERE MONTH(i.incomeDate) = :month AND YEAR(i.incomeDate) = :year")
    Page<IncomeEntity> findByMonth(@Param("month") int month, @Param("year") int year, Pageable pageable);

    @Query("SELECT SUM(i.incomeGive + i.incomeTenth + i.incomeBuilding + i.incomeService + i.incomeDonate + i.incomeOther) FROM IncomeEntity i WHERE MONTH(i.incomeDate) = :month AND YEAR(i.incomeDate) = :year")
    BigDecimal findTotalIncomeByMonth(@Param("month") int month, @Param("year") int year);

    @Query("SELECT i FROM IncomeEntity i WHERE " +
            "(:category = 'incomeGive' AND i.incomeGive IS NOT NULL) OR " +
            "(:category = 'incomeTenth' AND i.incomeTenth IS NOT NULL) OR " +
            "(:category = 'incomeBuilding' AND i.incomeBuilding IS NOT NULL) OR " +
            "(:category = 'incomeService' AND i.incomeService IS NOT NULL) OR " +
            "(:category = 'incomeDonate' AND i.incomeDonate IS NOT NULL) OR " +
            "(:category = 'incomeOther' AND i.incomeOther IS NOT NULL)")
    Page<IncomeEntity> findAllByCategory(@Param("category") String category, Pageable pageable);
}
