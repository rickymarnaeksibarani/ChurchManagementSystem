package ChurchManagementSystem.CMS.modules.asset.entity;

import ChurchManagementSystem.CMS.core.enums.AssetStatus;
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
    private AssetStatus status;

    @Nullable
    @Column(name = "brand") //merk
    private String brand;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;
}
