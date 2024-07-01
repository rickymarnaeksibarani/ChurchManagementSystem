package ChurchManagementSystem.CMS.modules.congregration.controller;

import ChurchManagementSystem.CMS.modules.congregration.dto.CongregrationDTO;
import ChurchManagementSystem.CMS.modules.congregration.dto.CongregrationRequestDto;
import ChurchManagementSystem.CMS.modules.congregration.entities.CongregrationEntity;
import ChurchManagementSystem.CMS.modules.congregration.service.CongregrationService;
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
@RequestMapping("/api/v1/congregration")
//@CrossOrigin(origins = "http://localhost:4200")
public class CongregrationController {
    @Autowired
    private CongregrationService congregrationService;

    //create
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCongregration(
            @Valid @RequestBody CongregrationDTO request
    ){
        try {
            CongregrationEntity result = congregrationService.createCongregration(request);
            ApiResponse<CongregrationEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data name!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Getting by Pagination
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchCongregration(CongregrationRequestDto searchDTO){
        try {
            PaginationUtil<CongregrationEntity, CongregrationEntity> result = congregrationService.getAllCongregrationByPagination(searchDTO);
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
            CongregrationEntity result = congregrationService.getCongregrationById(idCongregration);
            ApiResponse<CongregrationEntity> response = new ApiResponse<>(HttpStatus.OK, "Success retrievedd data people", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Update by Id
    @PutMapping(value = "/{idCongregration}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCongregration(
            @PathVariable("idCongregration") Long idCongregration,
            @Valid @RequestBody CongregrationDTO request
    ){
        try {
            CongregrationEntity result = congregrationService.updateCongregration(idCongregration, request);
            ApiResponse<CongregrationEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data congregration!", result);
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
            congregrationService.deleteCongregration(idCongregration);
            ApiResponse<String> response = new ApiResponse<>(HttpStatus.OK,"Success delete data", "DELETED");
            return new ResponseEntity<>(response, response.getStatus());
        } catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }
}
