package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateOutcomeRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outcomeDate;

    private BigDecimal outcomeDeposit = BigDecimal.ZERO;
    private BigDecimal outcomeBuilding = BigDecimal.ZERO;
    private BigDecimal outcomeDiakonia = BigDecimal.ZERO;
//    private BigDecimal outcomeGuest = BigDecimal.ZERO;
    private BigDecimal outcomeOperational = BigDecimal.ZERO;
    private BigDecimal outcomeEvent = BigDecimal.ZERO;
    private BigDecimal outcomeOther = BigDecimal.ZERO;

    private String description;
}
