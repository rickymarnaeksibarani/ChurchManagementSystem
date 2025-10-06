package ChurchManagementSystem.CMS.modules.financial.dto.income;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class IncomeResponeDto {
    private Long idIncome;
    private String nama;
    private BigDecimal totalIncome;
    private String deskripsi;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date incomeDate;

}
