package ChurchManagementSystem.CMS.modules.financial.dto;

import lombok.*;

import java.math.BigDecimal;

@Data
//@Builder
public class SummaryDto {

    private BigDecimal monthlyIncome;
    private BigDecimal monthlyOutcome;

    private BigDecimal totalIncome;   // all time
    private BigDecimal totalOutcome;  // all time

    private BigDecimal totalSummary;  // totalIncome - totalOutcome (all time)
    private BigDecimal balance;       // sama dengan totalSummary

    public SummaryDto(BigDecimal monthlyIncome, BigDecimal monthlyOutcome, BigDecimal totalSummary, BigDecimal balance, BigDecimal totalOutcome, BigDecimal totalIncome) {
        this.monthlyIncome = monthlyIncome;
        this.monthlyOutcome = monthlyOutcome;
        this.totalSummary = totalSummary;
        this.balance = balance;
        this.totalOutcome = totalOutcome;
        this.totalIncome = totalIncome;
    }
}
