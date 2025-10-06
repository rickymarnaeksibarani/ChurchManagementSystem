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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<IncomeEntity, Long>, JpaSpecificationExecutor<IncomeEntity> {

    List<IncomeEntity>findByIncomeDateBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT " +
            "COALESCE(SUM(i.persembahan), 0), " +
            "COALESCE(SUM(i.perpuluhan), 0), " +
            "COALESCE(SUM(i.pembangunan), 0), " +
            "COALESCE(SUM(i.service), 0), " +
            "COALESCE(SUM(i.donasi), 0), " +
            "COALESCE(SUM(i.lainnya), 0) " +
            "FROM IncomeEntity i")
    Object[] findTotalFinancialDetail();
}
