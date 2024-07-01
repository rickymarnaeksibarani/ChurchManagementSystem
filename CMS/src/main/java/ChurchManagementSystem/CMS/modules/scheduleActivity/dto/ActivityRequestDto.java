package ChurchManagementSystem.CMS.modules.scheduleActivity.dto;

import lombok.Data;

@Data
public class ActivityRequestDto {
    private Integer page;
    private Integer size;
    private String searchTerm;

    ActivityRequestDto(){
        if (this.getPage() == null){
            this.page = 1;
        }

        if (this.getSize() == null){
            this.size = 10;
        }
    }
}
