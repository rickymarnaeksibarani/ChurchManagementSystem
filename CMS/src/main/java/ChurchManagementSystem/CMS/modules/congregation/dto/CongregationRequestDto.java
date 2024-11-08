package ChurchManagementSystem.CMS.modules.congregation.dto;

import lombok.Data;

@Data
public class CongregationRequestDto {
    private Integer page;
    private Integer size;
    private String searchTerm;

    CongregationRequestDto(){
        if (this.getPage() == null){
            this.page = 1;
        }

        if (this.getSize() == null){
            this.size = 10;
        }
    }
}
