package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateOutcomeRequestDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date outcomeDate;

    private BigDecimal outcomeDeposit;
    private BigDecimal outcomeBuilding;
    private BigDecimal outcomeDiakonia;
//    private BigDecimal outcomeGuest = BigDecimal.ZERO;
    private BigDecimal outcomeOperational;
    private BigDecimal outcomeEvent;
    private BigDecimal outcomeOther;
    private String nama;

    private String description;
}
