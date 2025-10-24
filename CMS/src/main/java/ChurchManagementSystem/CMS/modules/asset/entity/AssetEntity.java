package ChurchManagementSystem.CMS.modules.asset.entity;

import ChurchManagementSystem.CMS.core.enums.AssetStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "master_asset")
public class AssetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "asset_name")
    private String assetName;

    @Column(name = "asset_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @Nullable
    @Column(name = "brand")
    private String brand;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    //todo: added created and update-at
}
