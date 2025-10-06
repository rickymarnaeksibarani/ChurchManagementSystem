package ChurchManagementSystem.CMS.modules.financial.dto.income;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

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
}
