package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

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
}
