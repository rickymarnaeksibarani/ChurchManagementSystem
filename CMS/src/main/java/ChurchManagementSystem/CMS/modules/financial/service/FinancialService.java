package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FinancialService {
    private BigDecimal calculateTotalIncome(IncomeEntity income) {
        return Optional.ofNullable(income.getIncomeGive()).orElse(BigDecimal.ZERO).add
                (Optional.ofNullable(income.getIncomeTenth()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(income.getIncomeBuilding()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(income.getIncomeService()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(income.getIncomeDonate()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(income.getIncomeOther()).orElse(BigDecimal.ZERO));
    }

    private BigDecimal calculateTotalOutcome(OutcomeEntity outcome){
        return Optional.ofNullable(outcome.getOutcomeDeposit()).orElse(BigDecimal.ZERO).add
                (Optional.ofNullable(outcome.getOutcomeBuilding()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(outcome.getOutcomeDiakonia()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(outcome.getOutcomeGuest()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(outcome.getOutcomeOperational()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(outcome.getOutcomeEvent()).orElse(BigDecimal.ZERO)).add
                (Optional.ofNullable(outcome.getOutcomeOther()).orElse(BigDecimal.ZERO));
    }

    public FinancialEntity calculateBalance(IncomeEntity income, OutcomeEntity outcome) {
        BigDecimal totalIncome = calculateTotalIncome(income);
        BigDecimal totalOutcome = calculateTotalOutcome(outcome);
        BigDecimal balance = totalIncome.subtract(totalOutcome);

        return new FinancialEntity(balance);
    }
}
