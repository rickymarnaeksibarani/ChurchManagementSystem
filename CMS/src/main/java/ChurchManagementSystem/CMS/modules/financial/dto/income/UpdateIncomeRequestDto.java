package ChurchManagementSystem.CMS.modules.financial.dto.income;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateIncomeRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date incomeDate;

    private BigDecimal incomeGive = BigDecimal.ZERO;
    private BigDecimal incomeTenth = BigDecimal.ZERO;
    private BigDecimal incomeBuilding = BigDecimal.ZERO;
    private BigDecimal incomeService = BigDecimal.ZERO;
    private BigDecimal incomeDonate = BigDecimal.ZERO;
    private BigDecimal incomeOther = BigDecimal.ZERO;

    private String description;
}
