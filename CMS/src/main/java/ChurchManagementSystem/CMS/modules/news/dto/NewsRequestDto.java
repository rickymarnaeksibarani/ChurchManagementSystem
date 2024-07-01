package ChurchManagementSystem.CMS.modules.news.dto;

import ChurchManagementSystem.CMS.core.enums.Category;
import lombok.Data;

import java.util.List;

@Data
public class NewsRequestDto {
    private Integer page;
    private Integer size;
    private String searchTerm;
    private List<Category> category;

    NewsRequestDto(){
        if (this.getPage()==null){
            this.page = 1;
        }
        if (this.getSize()==null){
            this.size = 10;
        }
    }
}