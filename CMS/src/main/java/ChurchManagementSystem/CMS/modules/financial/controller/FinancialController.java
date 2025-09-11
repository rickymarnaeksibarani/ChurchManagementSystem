package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.asset.dto.AssetRequestDto;
import ChurchManagementSystem.CMS.modules.asset.dto.AssetsDto;
import ChurchManagementSystem.CMS.modules.asset.entity.AssetEntity;
import ChurchManagementSystem.CMS.modules.financial.dto.IncomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.dto.IncomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.OutcomeResponeDto;
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
}
