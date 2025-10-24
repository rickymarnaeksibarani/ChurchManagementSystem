package ChurchManagementSystem.CMS.modules.scheduler;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class MaterializedViewScheduler {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Scheduled(cron = "* * 4 * * *")
    public void refreshBirthdateComing() {
        try {
            entityManager.createNativeQuery("REFRESH MATERIALIZED VIEW CONCURRENTLY public.materialized_view_birthdate_coming").executeUpdate();
            log.info("refresh materialized view for birthdate coming at: " + LocalDateTime.now());
        }catch (Exception e){
            log.info("refresh materialized was failed: "+ e.getMessage());
        }
    }
}
