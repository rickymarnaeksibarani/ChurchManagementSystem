package ChurchManagementSystem.CMS.modules.financial.dto.income;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class IncomeRequestDto {
//    private String searchTerm;
    private Integer page;
    private Integer size;
    private String category;
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
