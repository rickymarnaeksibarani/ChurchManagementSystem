package ChurchManagementSystem.CMS.modules.asset.dto;

import ChurchManagementSystem.CMS.core.enums.AssetCategory;
import lombok.Data;

import java.util.List;

@Data
public class AssetRequestDto {
    private Integer page;
    private Integer size;
    private String searchTerm;
    private List<AssetCategory> assetCategories;

    AssetRequestDto(){
        if (this.getPage()==null){
            this.page = 1;
        }
        if (this.getSize()==null){
            this.size = 10;
        }
    }
}
