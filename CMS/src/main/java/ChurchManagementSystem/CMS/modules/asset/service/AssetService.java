package ChurchManagementSystem.CMS.modules.asset.service;

import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.asset.dto.AssetRequestDto;
import ChurchManagementSystem.CMS.modules.asset.dto.AssetsDto;
import ChurchManagementSystem.CMS.modules.asset.entity.AssetEntity;
import ChurchManagementSystem.CMS.modules.asset.repository.AssetRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    //create
    public AssetEntity createAsset(AssetsDto request){
        AssetEntity data = new AssetEntity();
        data.setAssetName(request.getAssetName());
        data.setAssetCategory(request.getAssetCategory());
        data.setAssetDescription(request.getAssetDescription());
        data.setQuantity(request.getQuantity());
        return assetRepository.save(data);

    }

    //Getting by pagination
    public PaginationUtil<AssetEntity, AssetEntity> getAllAssetByPagination(AssetRequestDto searhRequest){
        Specification<AssetEntity> specification = (root, query, builder)-> {
            List<Predicate> predicates = new ArrayList<>();

            if (searhRequest.getSearchTerm()!=null){
                predicates.add(
                        builder.like(builder.upper(root.get("assetName")), "%" + searhRequest.getSearchTerm().toUpperCase()+"%" )
                );
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
        Pageable paging = PageRequest.of(searhRequest.getPage()-1, searhRequest.getSize());
        Page<AssetEntity> pagedResult = assetRepository.findAll(specification, paging);
        return new PaginationUtil<>(pagedResult, AssetEntity.class);
    }

    //Getting by ID
    public AssetEntity getAssetById(Long idAsset){
        AssetEntity result = assetRepository.findById(idAsset).orElse(null);
        if (result == null){
            throw new CustomRequestException("ID " + idAsset + " not found ", HttpStatus.NOT_FOUND);
        }
        return result;
    }

    @Transactional
    public AssetEntity updateAsset(Long idAsset, AssetsDto request){
        AssetEntity asset = assetRepository.findById(idAsset).orElseThrow(()-> new CustomRequestException("Asset does not exists", HttpStatus.CONFLICT));
        asset.setAssetName(request.getAssetName());
        asset.setAssetCategory(request.getAssetCategory());
        asset.setAssetDescription(request.getAssetDescription());
        asset.setQuantity(request.getQuantity());

        return assetRepository.save(asset);
    }

    @Transactional
    public void deleteAsset(Long idAsset){
        AssetEntity findData = assetRepository.findById(idAsset).orElseThrow(()->new CustomRequestException("Asset does not exists", HttpStatus.CONFLICT));
        assetRepository.delete(findData);
    }
}
