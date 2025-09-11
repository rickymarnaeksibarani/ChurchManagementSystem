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
import java.util.Date;
import java.util.List;

@Repository
public interface OutcomeRepository extends JpaRepository<OutcomeEntity, Long>, JpaSpecificationExecutor<OutcomeRepository> {
//    @Query("SELECT i FROM OutcomeEntity i WHERE MONTH(i.outcomeDate) = :month AND YEAR(i.outcomeDate) = :year")
//    Page<OutcomeEntity> findByMonth(@Param("month") int month, @Param("year") int year, Pageable pageable);
//
//    @Query("SELECT SUM(i.outcomeDeposit + i.outcomeBuilding + i.outcomeDiakonia + i.outcomeEvent + i.outcomeGuest + i.outcomeOperational + i.outcomeOther) FROM OutcomeEntity i WHERE MONTH(i.outcomeDate) = :month AND YEAR(i.outcomeDate) = :year")
//    BigDecimal findTotalOutcomeByMonth(@Param("month") int month, @Param("year") int year);
//
//    @Query("SELECT i FROM OutcomeEntity i WHERE " +
//            "(:category = 'outcomeDeposit' AND i.outcomeDeposit IS NOT NULL) OR " +
//            "(:category = 'outcomeBuilding' AND i.outcomeBuilding IS NOT NULL) OR " +
//            "(:category = 'outcomeDiakonia' AND i.outcomeDiakonia IS NOT NULL) OR " +
//            "(:category = 'outcomeGuest' AND i.outcomeGuest IS NOT NULL) OR " +
//            "(:category = 'outcomeOperational' AND i.outcomeOperational IS NOT NULL) OR " +
//            "(:category = 'outcomeEvent' AND i.outcomeEvent IS NOT NULL) OR" +
//            "(:category = 'outcomeOther' AND i.outcomeOther IS NOT NULL)")
//    Page<OutcomeEntity> findAllByCategory(@Param("category") String category, Pageable pageable);

    List<OutcomeEntity>findByOutcomeDateBetween(Date start, Date end);
}
