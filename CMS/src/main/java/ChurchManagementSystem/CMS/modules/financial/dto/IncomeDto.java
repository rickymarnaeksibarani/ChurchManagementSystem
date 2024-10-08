package ChurchManagementSystem.CMS.modules.financial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class IncomeDto {
    @JsonProperty("idIncome")
    private long idIncome;

    @JsonProperty("incomeDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date incomeDate;

    @JsonProperty("incomeGive")
    private BigDecimal incomeGive; //persembahan

    @JsonProperty("incomeTenth")
    private BigDecimal incomeTenth; //perpuluhan (hamuliateon)

    @JsonProperty("incomeBuilding")
    private BigDecimal incomeBuilding; //pembangunan

    @JsonProperty("incomeService")
    private BigDecimal incomeService; //jasa gereja

    @JsonProperty("incomeDonate")
    private BigDecimal incomeDonate; //donasi

    @JsonProperty("incomeOther")
    private BigDecimal incomeOther; //lainya

    @JsonProperty("description")
    private String description;
}
