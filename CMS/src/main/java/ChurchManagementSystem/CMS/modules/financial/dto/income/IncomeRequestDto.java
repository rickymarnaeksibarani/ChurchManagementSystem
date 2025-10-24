package ChurchManagementSystem.CMS.modules.financial.dto.income;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
public class IncomeRequestDto {
    private Integer page;
    private Integer size;
    private String category;
    private String year;
    private String month;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime periodStartTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime periodEndTime;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate specificDate;
    IncomeRequestDto(){
        {
            if(this.getPage() == null) {
                this.page = 1;
            }
            if(this.getSize() == null) {
                this.size = 10;
            }
        }
    }
}
