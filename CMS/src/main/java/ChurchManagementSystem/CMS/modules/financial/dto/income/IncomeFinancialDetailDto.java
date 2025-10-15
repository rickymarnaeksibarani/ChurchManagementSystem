package ChurchManagementSystem.CMS.modules.financial.dto.income;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncomeFinancialDetailDto {
    private BigDecimal totalPersembahan = BigDecimal.ZERO;
    private BigDecimal totalPerpuluhan = BigDecimal.ZERO;
    private BigDecimal totalPembangunan = BigDecimal.ZERO;
    private BigDecimal totalService = BigDecimal.ZERO;
    private BigDecimal totalDonasi = BigDecimal.ZERO;
    private BigDecimal totalLainnya = BigDecimal.ZERO;

}
