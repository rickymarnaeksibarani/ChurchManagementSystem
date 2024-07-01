package ChurchManagementSystem.CMS.modules.financial.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class IncomeDto {
    @JsonProperty("idIncome")
    private long idIncome;

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
}
