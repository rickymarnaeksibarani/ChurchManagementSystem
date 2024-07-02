package ChurchManagementSystem.CMS.modules.financial.repository;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
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
    List<OutcomeEntity> findByMonth(@Param("month") int month, @Param("year") int year);
}
