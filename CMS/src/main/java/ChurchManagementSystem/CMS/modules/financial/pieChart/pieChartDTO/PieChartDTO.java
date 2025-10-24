package ChurchManagementSystem.CMS.modules.financial.pieChart.pieChartDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PieChartDTO {
    private BigDecimal value;
    private String name;
}
