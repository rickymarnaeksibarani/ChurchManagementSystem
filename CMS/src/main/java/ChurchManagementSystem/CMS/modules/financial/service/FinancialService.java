package ChurchManagementSystem.CMS.modules.financial.service;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.IncomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.dto.IncomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.OutcomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.SummaryDto;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Service
@RequiredArgsConstructor
public class FinancialService {
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public IncomeEntity saveIncome (IncomeEntity incomeEntity){
        return incomeRepository.save(incomeEntity);
    }

    public PaginationUtil<IncomeEntity, IncomeResponeDto> getAllIncomeByPagination(IncomeRequestDto request) {
        Pageable paging = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("incomeDate").descending());
        Page<IncomeEntity> pagedResult = incomeRepository.findAll(paging);
        return new PaginationUtil<>(pagedResult, IncomeResponeDto.class);
    }

    public OutcomeEntity saveOutcome (OutcomeEntity outcomeEntity){
        return outcomeRepository.save(outcomeEntity);
    }

    public PaginationUtil<OutcomeEntity, OutcomeResponeDto> getAllOutcomeByPagination(IncomeRequestDto request) {
        Pageable paging = PageRequest.of(request.getPage() - 1, request.getSize(), Sort.by("outcomeDate").descending());
        Page<OutcomeEntity> pagedResult = outcomeRepository.findAll(paging);

        return new PaginationUtil<>(pagedResult, OutcomeResponeDto.class);
    }

    public SummaryDto getSummary(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDateTime start = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime end = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        BigDecimal monthlyIncome = incomeRepository.findByIncomeDateBetween(start, end)
                .stream().map(IncomeEntity::getTotalIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal monthlyOutcome = outcomeRepository.findByOutcomeDateBetween(start, end)
                .stream().map(OutcomeEntity::getTotalOutcome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalIncome = incomeRepository.findAll().stream()
                .map(IncomeEntity::getTotalIncome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalOutcome = outcomeRepository.findAll().stream()
                .map(OutcomeEntity::getTotalOutcome)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSummary = totalIncome.subtract(totalOutcome);
        BigDecimal balance = totalIncome.subtract(totalOutcome);

        return new SummaryDto(monthlyIncome, monthlyOutcome, totalSummary, balance, totalIncome, totalOutcome);
    }

}


