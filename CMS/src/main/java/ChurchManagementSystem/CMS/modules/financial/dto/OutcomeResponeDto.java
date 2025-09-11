package ChurchManagementSystem.CMS.modules.financial.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class OutcomeResponeDto {
    private Long idOutcome;
    private String nama;
    private BigDecimal totalOutcome;
    private String deskripsi;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date outcomeDate;
}
