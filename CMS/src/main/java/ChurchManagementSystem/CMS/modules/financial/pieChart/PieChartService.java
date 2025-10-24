package ChurchManagementSystem.CMS.modules.financial.pieChart;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.modules.financial.pieChart.pieChartDTOs.PieChartDTO;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PieChartService {
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public List<PieChartDTO> pieChart (IncomeRequestDto request){
        try {

            BigDecimal totalIncome = getTotalIncome(request);
            BigDecimal totalOutcome = getTotalOutcome(request);

            return List.of(
                    new PieChartDTO(totalIncome, "income"),
                    new PieChartDTO(totalOutcome, "outcome")
            );
        }catch (IllegalArgumentException e){throw new CustomRequestException("Failed to calculate total income & outcome " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);}
    }

    private BigDecimal getTotalIncome(IncomeRequestDto requestIncome) {
        if ("all".equalsIgnoreCase(requestIncome.getYear())) {
            return (requestIncome.getMonth() == null || requestIncome.getMonth().isBlank())
                    ? incomeRepository.sumTotalIncomeAllTime()
                    : incomeRepository.sumTotalIncomeByMonth(Month.valueOf(requestIncome.getMonth().toUpperCase()).getValue());
        }
        return (requestIncome.getMonth() == null || requestIncome.getMonth().isBlank())
                ? incomeRepository.sumTotalIncomeByYear(requestIncome.getYear())
                : incomeRepository.sumTotalIncomeByYearAndMonth(requestIncome.getYear(), Month.valueOf(requestIncome.getMonth().toUpperCase()).getValue());
    }

    private BigDecimal getTotalOutcome(IncomeRequestDto requestOutcome) {
        if ("all".equalsIgnoreCase(requestOutcome.getYear())) {
            return (requestOutcome.getMonth() == null || requestOutcome.getMonth().isBlank())
                    ? outcomeRepository.sumTotalOutcomeAllTime()
                    : outcomeRepository.sumTotalOutcomeByMonth(Month.valueOf(requestOutcome.getMonth().toUpperCase()).getValue());
        }
        return (requestOutcome.getMonth() == null || requestOutcome.getMonth().isBlank())
                ? outcomeRepository.sumTotalOutcomeByYear(requestOutcome.getYear())
                : outcomeRepository.sumTotalOutcomeByYearAndMonth(requestOutcome.getYear(), Month.valueOf(requestOutcome.getMonth().toUpperCase()).getValue());
    }



}
