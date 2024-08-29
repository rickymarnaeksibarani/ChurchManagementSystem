package ChurchManagementSystem.CMS.modules.financial.dto;

import ChurchManagementSystem.CMS.core.enums.Fungsi;
import ChurchManagementSystem.CMS.core.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class FinancialRequestDto {
    private Integer page;
    private Integer size;
    private String searchTerm;
    private List<Status> status;
    private List<Fungsi> fungsi;
    private Integer month;
    private Integer year;

    FinancialRequestDto(){
        if (this.getPage()==null){
            this.page = 1;
        }
        if (this.getSize()==null){
            this.size = 10;
        }
    }
}
