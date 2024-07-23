package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.repository.FinancialRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FinancialService {
    @Autowired
    private FinancialRepository financialRepository;
    @Autowired
    private IncomeRepository incomeRepository;
    @Autowired
    private OutcomeRepository outcomeRepository;

    public FinancialEntity calculateBalance(){
        List<IncomeEntity> incomes = incomeRepository.findAll();
        List<OutcomeEntity> outcomes = outcomeRepository.findAll();

        BigDecimal totalIncome = incomes.stream().map(
                income -> {
                    assert income.getIncomeDonate() != null;
                    return income.getIncomeDonate()
                            .add(income.getIncomeBuilding())
                            .add(income.getIncomeGive())
                            .add(income.getIncomeService())
                            .add(income.getIncomeTenth())
                            .add(income.getIncomeOther());
                }
        ).reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOutcome = outcomes.stream().map(
                outcome -> {
                    assert outcome.getOutcomeBuilding() != null;
                    return outcome.getOutcomeBuilding()
                            .add(outcome.getOutcomeDeposit())
                            .add(outcome.getOutcomeDiakonia())
                            .add(outcome.getOutcomeEvent())
                            .add(outcome.getOutcomeGuest())
                            .add(outcome.getOutcomeOperational())
                            .add(outcome.getOutcomeOther());
                }
        ).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new FinancialEntity(totalIncome, totalOutcome);
    }
    //Getting summary income by month
    public List<IncomeEntity> getIncomeByMonth(int month, int year) {
        return incomeRepository.findByMonth(month, year);
    }

    //Getting total income by month
    public BigDecimal getTotalIncomeByMonth(int month, int year) {
        return incomeRepository.findTotalIncomeByMonth(month, year);
    }

    //Getting total outcome by month
    public BigDecimal getTotalOutcomeByMonth(int month, int year){
        return outcomeRepository.findTotalOutcomeByMonth(month, year);
    }

    //Getting summary outcome by month
    public List<OutcomeEntity> getOutcomeByMonth(int month, int year){
        return outcomeRepository.findByMonth(month, year);
    }

    public IncomeEntity saveIncome(IncomeEntity income){
        return incomeRepository.save(income);
    }
    public OutcomeEntity saveOutcome(OutcomeEntity outcome){
        return outcomeRepository.save(outcome);
    }

    //TODO : PAGINATION\
}


