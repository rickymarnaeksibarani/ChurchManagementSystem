package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.IncomeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.OutcomeDto;
import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    public PaginationUtil<IncomeEntity, IncomeEntity> getAllIncome(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<IncomeEntity> pagedResult;

        if (category == null || category.isEmpty()) {
            pagedResult = incomeRepository.findAll(pageable);
        } else {
            pagedResult = incomeRepository.findAllByCategory(category, pageable);
            pagedResult.getContent().forEach(income -> {
                if (!category.equals("incomeGive")) income.setIncomeGive(null);
                if (!category.equals("incomeTenth")) income.setIncomeTenth(null);
                if (!category.equals("incomeBuilding")) income.setIncomeBuilding(null);
                if (!category.equals("incomeService")) income.setIncomeService(null);
                if (!category.equals("incomeDonate")) income.setIncomeDonate(null);
                if (!category.equals("incomeOther")) income.setIncomeOther(null);
            });
        }
        return new PaginationUtil<>(pagedResult, IncomeEntity.class);
    }

    public PaginationUtil<OutcomeEntity, OutcomeEntity> getAllOutcome(int page, int size, String category) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<OutcomeEntity> pagedResult;

        if (category == null || category.isEmpty()) {
            pagedResult = outcomeRepository.findAll(pageable);
        } else {
            pagedResult = outcomeRepository.findAllByCategory(category, pageable);
            pagedResult.getContent().forEach(outcome -> {
                if (!category.equals("outcomeDeposit")) outcome.setOutcomeDeposit(null);
                if (!category.equals("outcomeBuilding")) outcome.setOutcomeBuilding(null);
                if (!category.equals("outcomeDiakonia")) outcome.setOutcomeDiakonia(null);
                if (!category.equals("outcomeGuest")) outcome.setOutcomeGuest(null);
                if (!category.equals("outcomeOperational")) outcome.setOutcomeOperational(null);
                if (!category.equals("outcomeEvent")) outcome.setOutcomeEvent(null);
                if (!category.equals("outcomeOther")) outcome.setOutcomeOther(null);

            });
        }
        return new PaginationUtil<>(pagedResult, OutcomeEntity.class);
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
    public IncomeEntity saveIncome(@Valid IncomeDto income){
        try {
            IncomeEntity data = IncomeEntity.builder()
                    .incomeDate(income.getIncomeDate())
                    .incomeGive(income.getIncomeGive())
                    .incomeTenth(income.getIncomeTenth())
                    .incomeBuilding(income.getIncomeBuilding())
                    .incomeService(income.getIncomeService())
                    .incomeDonate(income.getIncomeDonate())
                    .incomeOther(income.getIncomeOther())
                    .description(income.getDescription())
                    .build();
            return incomeRepository.save(data);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public OutcomeEntity saveOutcome(@Valid OutcomeDto outcome){
        try {
            OutcomeEntity data = OutcomeEntity.builder()
                    .outcomeDate(outcome.getOutcomeDate())
                    .outcomeDeposit(outcome.getOutcomeDeposit())
                    .outcomeBuilding(outcome.getOutcomeBuilding())
                    .outcomeDiakonia(outcome.getOutcomeDiakonia())
                    .outcomeGuest(outcome.getOutcomeGuest())
                    .outcomeOperational(outcome.getOutcomeOperational())
                    .outcomeEvent(outcome.getOutcomeEvent())
                    .outcomeOther(outcome.getOutcomeOther())
                    .description(outcome.getDescription())
                    .build();
            return outcomeRepository.save(data);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public IncomeEntity updateIncome(Long idIncome, IncomeDto request){
        try {
            IncomeEntity income = incomeRepository.findById(idIncome)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + idIncome + " not found"));
            income.setIncomeDate(request.getIncomeDate());
            income.setIncomeGive(request.getIncomeGive());
            income.setIncomeTenth(request.getIncomeTenth());
            income.setIncomeBuilding(request.getIncomeBuilding());
            income.setIncomeService(request.getIncomeService());
            income.setIncomeDonate(request.getIncomeDonate());
            income.setIncomeOther(request.getIncomeOther());
            income.setDescription(request.getDescription());
            income.setUpdateAt(LocalDateTime.now());
            return incomeRepository.save(income);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public OutcomeEntity updateOutcome(Long idOutcome, OutcomeDto request) {
        try {
            OutcomeEntity outcome = outcomeRepository.findById(idOutcome).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "ID " + idOutcome + " not found"));
            outcome.setOutcomeDate(request.getOutcomeDate());
            outcome.setOutcomeDeposit(request.getOutcomeDeposit());
            outcome.setOutcomeBuilding(request.getOutcomeBuilding());
            outcome.setOutcomeDiakonia(request.getOutcomeDiakonia());
            outcome.setOutcomeGuest(request.getOutcomeGuest());
            outcome.setOutcomeOperational(request.getOutcomeOperational());
            outcome.setOutcomeEvent(request.getOutcomeEvent());
            outcome.setOutcomeOther(request.getOutcomeOther());
            outcome.setDescription(request.getDescription());
            outcome.setUpdateAt(LocalDateTime.now());
            return outcomeRepository.save(outcome);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}


