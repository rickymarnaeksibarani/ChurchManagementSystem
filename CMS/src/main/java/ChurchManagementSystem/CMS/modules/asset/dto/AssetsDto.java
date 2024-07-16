package ChurchManagementSystem.CMS.modules.asset.dto;

import ChurchManagementSystem.CMS.core.enums.AssetStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class AssetsDto {
    @JsonProperty("id")
    private long id;

    @JsonProperty("assetName")
    private String assetName;

    @Nullable
    @JsonProperty("brand")
    private String brand;

    @Enumerated
    private AssetStatus status;

    @JsonProperty("description")
    private String description;

    @JsonProperty("quantity")
    private int quantity;

}
