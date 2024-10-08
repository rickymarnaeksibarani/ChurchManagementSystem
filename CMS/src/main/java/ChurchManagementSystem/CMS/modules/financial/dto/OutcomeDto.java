package ChurchManagementSystem.CMS.modules.financial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OutcomeDto {
    @JsonProperty("idOutcome")
    private long idOutcome;

    @JsonProperty("outcomeDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date outcomeDate;

    @JsonProperty("outcomeDeposit")
    private BigDecimal outcomeDeposit; //setor

    @JsonProperty("outcomeBuilding")
    private BigDecimal outcomeBuilding; //pembangunan

    @JsonProperty("outcomeDiakonia")
    private BigDecimal outcomeDiakonia; //diakonia

    @JsonProperty("outcomeGuest")
    private BigDecimal outcomeGuest; //tamu

    @JsonProperty("outcomeOperational")
    private BigDecimal outcomeOperational; //operasional

    @JsonProperty("outcomeEvent")
    private BigDecimal outcomeEvent; //kegiatan

    @JsonProperty("outcomeOther")
    private BigDecimal outcomeOther; //lainnya

    @JsonProperty("description")
    private String description;
}
