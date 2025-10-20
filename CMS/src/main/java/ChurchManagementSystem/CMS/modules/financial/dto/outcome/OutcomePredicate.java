package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OutcomePredicate {
    public static Specification<OutcomeEntity> category(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }

            switch (category.toLowerCase()) {
                case "deposit":
                case "pembangunan":
                case "diakonia":
                case "pelayanan":
                case "operasional":
                case "acara":
                case "lainnya":
                    return criteriaBuilder.greaterThan(root.get(category), BigDecimal.ZERO);

                default:
                    // Jika category tidak valid, tidak melakukan filter apa-apa
                    return null;
            }
        };
    }

    public static Specification<OutcomeEntity> betweenDates(LocalDateTime periodStartTime, LocalDateTime periodEndTime) {
        return (root, query, cb) -> {
            if (periodStartTime != null && periodEndTime != null) {
                LocalDateTime adjustedEnd = periodEndTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
                return cb.between(root.get("outcomeDate"), periodStartTime, adjustedEnd);
            } else if (periodStartTime != null) {
                // >= dari outcomeDate
                return cb.greaterThanOrEqualTo(root.get("outcomeDate"), periodStartTime);
            } else if (periodEndTime != null) {
                LocalDateTime adjustedEnd = periodEndTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
//                 <= dari outcomeDate
                return cb.lessThanOrEqualTo(root.get("outcomeDate"), adjustedEnd);
            }
            return cb.conjunction();
        };
    }

    public static Specification<OutcomeEntity> specificDate(LocalDate specificDate) {
        return (root, query, cb) -> {
            if (specificDate != null) {
                LocalDateTime startOfDay = specificDate.atStartOfDay();
                LocalDateTime endOfDay = specificDate.atTime(23, 59, 59);
                return cb.between(root.get("outcomeDate"), startOfDay, endOfDay);
            }
            return cb.conjunction();
        };
    }
}
