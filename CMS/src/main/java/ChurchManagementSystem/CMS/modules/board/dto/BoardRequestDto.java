package ChurchManagementSystem.CMS.modules.board.dto;

import ChurchManagementSystem.CMS.core.enums.Fungsi;
import ChurchManagementSystem.CMS.core.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class BoardRequestDto {
    private Integer page;
    private Integer size;
    private String searchByName;
    private Fungsi filterByFungsi;
    private Status filterByStatus;

    BoardRequestDto(){
        if (this.getPage()==null){
            this.page = 1;
        }
        if (this.getSize()==null){
            this.size = 10;
        }
    }
}
