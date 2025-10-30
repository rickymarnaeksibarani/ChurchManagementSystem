package ChurchManagementSystem.CMS.modules.asset.controller;

import ChurchManagementSystem.CMS.core.customResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.asset.dto.AssetRequestDto;
import ChurchManagementSystem.CMS.modules.asset.dto.AssetsDto;
import ChurchManagementSystem.CMS.modules.asset.entity.AssetEntity;
import ChurchManagementSystem.CMS.modules.asset.service.AssetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/asset")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAsset(
            @Valid @RequestBody AssetsDto request
    ){
        try {
            AssetEntity result = assetService.createAsset(request);
            ApiResponse<AssetEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data asset!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting by Pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchAsset(AssetRequestDto searchDTO){
        try {
            PaginationUtil<AssetEntity, AssetsDto> result = assetService.getAllAssetByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data assets", result), HttpStatus.OK);
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting by Id
    @GetMapping(value = "/{idAsset}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAssetById(
            @PathVariable("idAsset") Long idAsset
    ){
        try {
            AssetEntity result = assetService.getAssetById(idAsset);
            ApiResponse<AssetEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrievedd data asset", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update by Id
    @PutMapping(value = "/{idAsset}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAsset(
            @PathVariable("idAsset") Long idAsset,
            @Valid @RequestBody AssetsDto request
    ){
        try {
            AssetEntity result = assetService.updateAsset(idAsset, request);
            ApiResponse<AssetEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data asset!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Delete by Id
    @DeleteMapping(value = "/{idAsset}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteAsset(
            @PathVariable("idAsset") Long idAsset
    ){
        try{
            assetService.deleteAsset(idAsset);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK,"Success delete data", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
