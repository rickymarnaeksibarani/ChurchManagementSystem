package ChurchManagementSystem.CMS.modules.financial.pieChart;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.modules.financial.pieChart.pieChartDTO.PieChartDTO;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/donat-chart")
@RequiredArgsConstructor
public class PieChartController {
    private final PieChartService pieChartService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDonatChart(@ModelAttribute IncomeRequestDto searchDTO) {
        try {
            List<PieChartDTO> series = pieChartService.pieChart(searchDTO);

            Map<String, Object> response = new HashMap<>();
            response.put("series", series);
            return new ResponseEntity<>(new ApiResponePieChart<>(HttpStatus.OK, "Success retrieved data income", series), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
