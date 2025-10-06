package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutcomeFinancialDetailItemDto {
    private Date outcomeDate;
    private String category;
    private BigDecimal nominal;
    private String keterangan;
}
