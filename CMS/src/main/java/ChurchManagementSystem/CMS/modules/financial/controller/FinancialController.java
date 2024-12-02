package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.IncomeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.OutcomeDto;
import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/financial")
public class FinancialController {
    @Autowired
    private FinancialService financialService;

    //Getting balance
    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public FinancialEntity getBalance(){

        return financialService.calculateBalance();
    }
//    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
//    public PaginationUtil<FinancialEntity, FinancialEntity> getBalance(){
//        return financialService.calculateBalance();
//    }

    @GetMapping(value = "/income/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationUtil<IncomeEntity, IncomeEntity> getAllIncome(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String category)
    {
        return financialService.getAllIncome(page, size, category);
    }

    @GetMapping(value = "/outcome/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationUtil<OutcomeEntity, OutcomeEntity>getAllOutcome(
            @RequestParam(defaultValue = "1")int page,
            @RequestParam(defaultValue = "10")int size,
            @RequestParam(required = false)String category)
    {
        return financialService.getAllOutcome(page, size, category);
    }

    //Getting Income summary by month
    @GetMapping(value = "/income/byMounth", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationUtil<IncomeEntity, IncomeEntity> getIncomeByMonth(@RequestParam int month,
                                                                       @RequestParam int year,
                                                                       @RequestParam(defaultValue = "1")int page,
                                                                       @RequestParam(defaultValue = "10")int size) {
        return financialService.getIncomeByMonth(month, year, page, size);
    }
    //Getting Outcome summary by mounth
    @GetMapping(value = "/outcome/byMounth", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationUtil<OutcomeEntity, OutcomeEntity> getOutcomeByMonth(@RequestParam int month,
                                                                          @RequestParam int year,
                                                                          @RequestParam(defaultValue = "1")int page,
                                                                          @RequestParam(defaultValue = "10")int size){
        return financialService.getOutcomeByMonth(month, year, page, size);
    }

    //Getting total income by month
    @GetMapping(value = "/income/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTotalIncomeByMonth(@RequestParam int month, @RequestParam int year) {
        return financialService.getTotalIncomeByMonth(month, year);
    }

    //Getting total outcome by month
    @GetMapping(value = "/outcome/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTotalOutcomeByMonth(@RequestParam int month, @RequestParam int year){
        return financialService.getTotalOutcomeByMonth(month, year);
    }

    @PostMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addIncome(
            @Valid @RequestBody IncomeDto income
    ){
        try {
            IncomeEntity result = financialService.saveIncome(income);
            ApiResponse<IncomeEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data income!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //Create Outcome
    @PostMapping(value = "/outcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addOutcome(
            @Valid @RequestBody OutcomeDto outcome
    ){
        try {
            OutcomeEntity result = financialService.saveOutcome(outcome);
            ApiResponse<OutcomeEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Success create data outcome!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    //update
    @PutMapping(value = "/income/{idIncome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateIncome(
            @PathVariable("idIncome") Long idIncome,
            @Valid @RequestBody IncomeDto request
    ){
        try {
            IncomeEntity result = financialService.updateIncome(idIncome, request);
            ApiResponse<IncomeEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data income!", result);
            return new ResponseEntity<>(response, response.getStatus());
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PutMapping(value = "/outcome/{idOutcome}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOutcome(
            @PathVariable("idOutcome") Long idOutcome,
            @Valid @RequestBody OutcomeDto request
    ){
        try {
            OutcomeEntity result = financialService.updateOutcome(idOutcome, request);
            ApiResponse<OutcomeEntity> response = new ApiResponse<>(HttpStatus.ACCEPTED, "Success update data outcome", result);
            return new ResponseEntity<>(response, response.getStatus());
        }catch (CustomRequestException e){
            return e.GlobalCustomRequestException(e.getMessage(), e.getStatus());
        }
    }
}
