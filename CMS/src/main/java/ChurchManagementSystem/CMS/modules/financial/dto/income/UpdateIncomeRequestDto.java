package ChurchManagementSystem.CMS.modules.financial.dto.income;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateIncomeRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date incomeDate;

    private BigDecimal incomeGive;
    private BigDecimal incomeTenth;
    private BigDecimal incomeBuilding;
    private BigDecimal incomeService;
    private BigDecimal incomeDonate;
    private BigDecimal incomeOther;
    private String nama;

    private String description;
}
