package ChurchManagementSystem.CMS.modules.financial.pieChart;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.modules.financial.pieChart.pieChartDTO.PieChartDTO;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.repository.IncomeRepository;
import ChurchManagementSystem.CMS.modules.financial.repository.OutcomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PieChartService {
    private final IncomeRepository incomeRepository;
    private final OutcomeRepository outcomeRepository;

    public List<PieChartDTO> pieChart (IncomeRequestDto request){

        try {
            BigDecimal totalIncome = BigDecimal.ZERO;
            BigDecimal totalOutcome = BigDecimal.ZERO;
//            BigDecimal totalIncome = incomeRepository.sumTotalIncomeAllTime();
//            BigDecimal totalOutcome = outcomeRepository.sumTotalOutcomeAllTime();
//
//            List<PieChartDTO> series = new ArrayList<>();
//            series.add(new PieChartDTO(totalIncome, "income"));
//            series.add(new PieChartDTO(totalOutcome, "outcome"));
//
//            return series;
            if ("all".equalsIgnoreCase(request.getYear())) {
                if (request.getMonth() == null || request.getMonth().isBlank()) {
                    totalIncome = incomeRepository.sumTotalIncomeAllTime();
                    totalOutcome = outcomeRepository.sumTotalOutcomeAllTime();
                } else {
                    int monthNumber = Month.valueOf(request.getMonth().toUpperCase()).getValue();
                    totalIncome = incomeRepository.sumTotalIncomeByMonth(monthNumber);
                    totalOutcome = outcomeRepository.sumTotalOutcomeByMonth(monthNumber);
                }
            }
            else {
                if (request.getMonth() == null || request.getMonth().isBlank()) {
                    totalIncome = incomeRepository.sumTotalIncomeByYear(request.getYear());
                    totalOutcome = outcomeRepository.sumTotalOutcomeByYear(request.getYear());
                } else {
                    int monthNumber = Month.valueOf(request.getMonth().toUpperCase()).getValue();
                    totalIncome = incomeRepository.sumTotalIncomeByYearAndMonth(request.getYear(), monthNumber);
                    totalOutcome = outcomeRepository.sumTotalOutcomeByYearAndMonth(request.getYear(), monthNumber);
                }
            }

            List<PieChartDTO> series = new ArrayList<>();
            series.add(new PieChartDTO(totalIncome, "income"));
            series.add(new PieChartDTO(totalOutcome, "outcome"));
            return series;

        }catch (IllegalArgumentException e){throw new CustomRequestException("Failed to calculate total income & outcome " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);}
    }


}
