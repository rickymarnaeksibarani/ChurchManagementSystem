package ChurchManagementSystem.CMS.modules.congregration.dto;

import lombok.Data;

@Data
public class CongregrationRequestDto {
    private Integer page;
    private Integer size;
    private String searchTerm;

    CongregrationRequestDto(){
        if (this.getPage() == null){
            this.page = 1;
        }

        if (this.getSize() == null){
            this.size = 10;
        }
    }
}
