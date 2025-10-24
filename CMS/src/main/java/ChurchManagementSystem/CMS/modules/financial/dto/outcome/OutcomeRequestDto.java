package ChurchManagementSystem.CMS.modules.financial.dto.outcome;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class OutcomeRequestDto {
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
    OutcomeRequestDto(){
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
