package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeFinancialDetailDto;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeFinancialDetailDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.SummaryDto;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/financial")
@RequiredArgsConstructor
public class FinancialController {
    private final FinancialService financialService;

    @PostMapping("/income")
    public IncomeEntity createIncome(@RequestBody IncomeEntity income) {
        return financialService.saveIncome(income);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/income/pagination")
    public ResponseEntity<?> getAllIncome(IncomeRequestDto searchDTO){
        try {
            PaginationUtil<IncomeEntity, IncomeResponeDto> result = financialService.getAllIncomeByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data income", result), HttpStatus.OK);
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping("/outcome")
    public OutcomeEntity createOutcome(@RequestBody OutcomeEntity outcome) {
        return financialService.saveOutcome(outcome);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/outcome/pagination")
    public ResponseEntity<?> getAllOutcome(IncomeRequestDto searchDTO){
        try {
            PaginationUtil<OutcomeEntity, OutcomeResponeDto> result = financialService.getAllOutcomeByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data outcome", result), HttpStatus.OK);
        }
        catch (CustomRequestException error){
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryDto> getSummary(
            @RequestParam int year,
            @RequestParam int month
    ) {
        SummaryDto summary = financialService.getSummary(year, month);
        return ResponseEntity.ok(summary);
    }

//    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/income/financial-detail")
//    public ResponseEntity<?> getFinancialDetailByIncome() {
//        try {
//            IncomeFinancialDetailDto financialDetail = financialService.getFinancialDetailByIncome();
//            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved financial detail", financialDetail), HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve financial detail", null), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    getFinancialDetailByIncome

    @GetMapping(value = "/income/financial-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFinancialDetailByIncome(
            @RequestParam(name = "category", required = false) String category) {
        try {
            IncomeFinancialDetailDto result = financialService.getFinancialDetailByIncome(category);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved financial detail", result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve financial detail", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/outcome/financial-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFinancialDetailByOutcome(
            @RequestParam(name = "category", required = false) String category) {
        try {
            OutcomeFinancialDetailDto result = financialService.getFinancialDetailByOutcome(category);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved financial detail", result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to retrieve financial detail", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
