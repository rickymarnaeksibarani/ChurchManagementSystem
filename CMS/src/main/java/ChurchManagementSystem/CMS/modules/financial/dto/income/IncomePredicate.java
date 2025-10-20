package ChurchManagementSystem.CMS.modules.financial.dto.income;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class IncomePredicate {

    public static Specification<IncomeEntity> category(String category) {
        return (root, query, criteriaBuilder) -> {
            if (category == null) {
                return null;
            }

            switch (category.toLowerCase()) {
                case "persembahan":
                case "perpuluhan":
                case "pembangunan":
                case "service":
                case "donasi":
                    return criteriaBuilder.greaterThan(root.get(category), BigDecimal.ZERO);

                default:
                    // Jika category tidak valid, tidak melakukan filter apa-apa
                    return null;
            }
        };
    }

    public static Specification<IncomeEntity> betweenDates(LocalDateTime periodStartTime, LocalDateTime periodEndTime) {
        return (root, query, cb) -> {
            if (periodStartTime != null && periodEndTime != null) {
                LocalDateTime adjustedEnd = periodEndTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
                return cb.between(root.get("incomeDate"), periodStartTime, adjustedEnd);
            } else if (periodStartTime != null) {
                // >= dari incomeDate
                return cb.greaterThanOrEqualTo(root.get("incomeDate"), periodStartTime);
            } else if (periodEndTime != null) {
                LocalDateTime adjustedEnd = periodEndTime.withHour(23).withMinute(59).withSecond(59).withNano(999_999_999);
//                 <= dari incomeDate
                return cb.lessThanOrEqualTo(root.get("incomeDate"), adjustedEnd);
            }
            return cb.conjunction();
        };
    }

    public static Specification<IncomeEntity> specificDate(LocalDate specificDate) {
        return (root, query, cb) -> {
            if (specificDate != null) {
                LocalDateTime startOfDay = specificDate.atStartOfDay();
                LocalDateTime endOfDay = specificDate.atTime(23, 59, 59);
                return cb.between(root.get("incomeDate"), startOfDay, endOfDay);
            }
            return cb.conjunction();
        };
    }


}
