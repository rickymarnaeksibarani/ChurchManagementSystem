package ChurchManagementSystem.CMS.modules.financial.dto.income;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeFinancialDetailItemDto {
    private Date incomeDate;
    private String category; // category name
    private BigDecimal nominal;
    private String keterangan;
}
