package ChurchManagementSystem.CMS.modules.financial.pieChart.pieChartDTOs;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponePieChart<T> {
    private HttpStatusCode status;
    private String message;
    private T series;

    public ApiResponePieChart(HttpStatusCode status, String message, T series) {
        this.status = status;
        this.message = message;
        this.series = series;
    }
}
