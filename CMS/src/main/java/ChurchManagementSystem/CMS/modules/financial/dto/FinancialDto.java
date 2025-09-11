package ChurchManagementSystem.CMS.modules.financial.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinancialDto {

    private BigDecimal monthlyIncome;
    private BigDecimal monthlyOutcome;

    private BigDecimal totalIncome;
    private BigDecimal totalOutcome;

    private BigDecimal balance;
}
