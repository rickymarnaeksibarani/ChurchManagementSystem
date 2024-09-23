package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class FinancialService {
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
    public PaginationUtil<IncomeEntity, IncomeEntity> getIncomeByMonth(int month, int year, int page, int size){
        Pageable pageable = PageRequest.of(page -1,size);
        Page<IncomeEntity> pagedResult = incomeRepository.findByMonth(month, year, pageable);
        return new PaginationUtil<>(pagedResult, IncomeEntity.class);
    }

    //Getting summary outcome by month
    public PaginationUtil<OutcomeEntity, OutcomeEntity>getOutcomeByMonth(int month, int year, int page, int size){
        Pageable pageable = PageRequest.of(page-1, size);
        Page<OutcomeEntity>pagedResult = outcomeRepository.findByMonth(month, year, pageable);
        return new PaginationUtil<>(pagedResult, OutcomeEntity.class);
    }

    //Getting total income by month
    public String getTotalIncomeByMonth(int month, int year) {
        BigDecimal totalIncome =  incomeRepository.findTotalIncomeByMonth(month, year);
        if (totalIncome == null){
            return "Total Income 0.00";
        }else {
            return "Total Income "+totalIncome;
        }
    }

    //Getting total outcome by month
    public String getTotalOutcomeByMonth(int month, int year){
        BigDecimal totalOutcome = outcomeRepository.findTotalOutcomeByMonth(month, year);
        if (totalOutcome == null){
            return "Total Outcome 0.00";
        }else {
            return "Total Outcome " + totalOutcome;
        }
    }
    public IncomeEntity saveIncome(IncomeEntity income){
        return incomeRepository.save(income);
    }
    public OutcomeEntity saveOutcome(OutcomeEntity outcome){
        return outcomeRepository.save(outcome);
    }
    public IncomeEntity updateIncome(IncomeEntity income) {
        return incomeRepository.save(income);
    }
    public OutcomeEntity updateOutcome(OutcomeEntity outcome){
        return outcomeRepository.save(outcome);
    }
}


