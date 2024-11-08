package ChurchManagementSystem.CMS.modules.congregation.controller;

import ChurchManagementSystem.CMS.modules.congregation.dto.CongregationDTO;
import ChurchManagementSystem.CMS.modules.congregation.dto.CongregationRequestDto;
import ChurchManagementSystem.CMS.modules.congregation.entities.CongregationEntity;
import ChurchManagementSystem.CMS.modules.congregation.service.CongregationService;
import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/congregation")
public class CongregationController {
    @Autowired
    private CongregationService congregationService;

    //create
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCongregration(
            @Valid @RequestBody CongregationDTO request
    ){
        try {
            CongregationEntity result = congregationService.createCongregration(request);
            ApiResponse<CongregationEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data name!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting by Pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchCongregration(CongregationRequestDto searchDTO){
        try {
            PaginationUtil<CongregationEntity, CongregationEntity> result = congregationService.getAllCongregrationByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data name", result), HttpStatus.OK);
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting by Id
    @GetMapping(value = "/{idCongregration}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCongregrationById(
            @PathVariable("idCongregration") Long idCongregration
    ){
        try {
            CongregationEntity result = congregationService.getCongregrationById(idCongregration);
            ApiResponse<CongregationEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrievedd data people", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update by Id
    @PutMapping(value = "/{idCongregration}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCongregration(
            @PathVariable("idCongregration") Long idCongregration,
            @Valid @RequestBody CongregationDTO request
    ){
        try {
            CongregationEntity result = congregationService.updateCongregration(idCongregration, request);
            ApiResponse<CongregationEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data congregration!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Delete by Id
    @DeleteMapping(value = "/{idCongregration}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteCongregration(
            @PathVariable("idCongregration") Long idCongregration
    ){
        try{
            congregationService.deleteCongregration(idCongregration);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK,"Success delete data", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
