package ChurchManagementSystem.CMS.modules.financial.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinancialDto {

    private BigDecimal monthlyIncome;
    private BigDecimal monthlyOutcome;

    private BigDecimal totalIncome;   // all time
    private BigDecimal totalOutcome;  // all time

    private BigDecimal balance;
}
