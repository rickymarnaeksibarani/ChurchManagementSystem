package ChurchManagementSystem.CMS.modules.asset.entity;

import ChurchManagementSystem.CMS.core.enums.AssetCategory;
import ChurchManagementSystem.CMS.core.enums.Fungsi;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "master_asset")
public class AssetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetCategory assetCategory;

    @Nullable
    @Column(name = "type") //merk
    private String type;

    @Column(name = "asset_description", columnDefinition = "text")
    private String assetDescription;

    @Column(name = "quantity")
    private Integer quantity;
}
