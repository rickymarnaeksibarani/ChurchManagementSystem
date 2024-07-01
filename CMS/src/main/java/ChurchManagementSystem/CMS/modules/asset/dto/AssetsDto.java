package ChurchManagementSystem.CMS.modules.asset.dto;

import ChurchManagementSystem.CMS.core.enums.AssetCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AssetsDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("assetName")
    private String assetName;

    @Enumerated
    private AssetCategory assetCategory;

    @JsonProperty("assetDescription")
    private String assetDescription;

    @JsonProperty("quantity")
    private int quantity;

}
