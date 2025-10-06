package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutcomeFinancialDetailDto {

    private BigDecimal totalDeposit = BigDecimal.ZERO;
    private BigDecimal totalPembangunan = BigDecimal.ZERO;
    private BigDecimal totalDiakonia = BigDecimal.ZERO;
    private BigDecimal totalPelayanan = BigDecimal.ZERO;
    private BigDecimal totalOperasional = BigDecimal.ZERO;
    private BigDecimal totalAcara = BigDecimal.ZERO;
    private BigDecimal totalLainnya = BigDecimal.ZERO;

    private List<OutcomeFinancialDetailItemDto>details = new ArrayList<>();
}
