package ChurchManagementSystem.CMS.modules.board.dto;

import ChurchManagementSystem.CMS.core.enums.Fungsi;
import ChurchManagementSystem.CMS.core.enums.Status;
import ChurchManagementSystem.CMS.modules.board.entity.BoardEntity;
import org.springframework.data.jpa.domain.Specification;

public class BoardPredicateDto {
    public static Specification<BoardEntity> searchByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name != null && !name.isEmpty()) {
                return criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("name")),
                        "%" + name.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static Specification<BoardEntity> filterByFungsi(Fungsi fungsi) {
        return (root, query, criteriaBuilder) -> {
            if (fungsi != null) {
                return criteriaBuilder.equal(root.get("fungsi"), fungsi);
            }
            return null;
        };
    }

    public static Specification<BoardEntity> filterByStatus(Status status) {
        return (root, query, criteriaBuilder) -> {
            if (status != null) {
                return criteriaBuilder.equal(root.get("status"), status);
            }
            return null;
        };
    }
}
