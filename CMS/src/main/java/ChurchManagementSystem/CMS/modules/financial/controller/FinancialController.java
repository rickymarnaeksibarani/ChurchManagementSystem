package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
import ChurchManagementSystem.CMS.core.Exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeFinancialDetailDto;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeFinancialDetailItemDto;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.dto.income.IncomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeFinancialDetailDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeFinancialDetailItemDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.dto.SummaryDto;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/financial")
@RequiredArgsConstructor
public class FinancialController {
    private final FinancialService financialService;

    @PostMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeEntity createIncome(@RequestBody IncomeEntity income) {
        return financialService.saveIncome(income);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/income/pagination")
    public ResponseEntity<?> getAllIncome(IncomeRequestDto searchDTO) {
        try {
            PaginationUtil<IncomeEntity, IncomeResponeDto> result = financialService.getAllIncomeByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data income", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
            return error.GlobalCustomRequestException(error.getMessage(), error.getStatus());
        }
    }

    @PostMapping(value = "/outcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutcomeEntity createOutcome(@RequestBody OutcomeEntity outcome) {
        return financialService.saveOutcome(outcome);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/outcome/pagination")
    public ResponseEntity<?> getAllOutcome(IncomeRequestDto searchDTO) {
        try {
            PaginationUtil<OutcomeEntity, OutcomeResponeDto> result = financialService.getAllOutcomeByPagination(searchDTO);
            return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, "Success retrieved data outcome", result), HttpStatus.OK);
        } catch (CustomRequestException error) {
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

    @GetMapping(value = "/income/financial-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getFinancialDetailByIncome(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pair<IncomeFinancialDetailDto, PaginationUtil<IncomeFinancialDetailItemDto, IncomeFinancialDetailItemDto>> result =
                financialService.getFinancialDetailByIncome(category, page, size);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "OK");
        response.put("message", "Success retrieved financial detail");
        response.put("result", result.getLeft());

        // Tampilkan pagination hanya jika category dikirim
        if (category != null && !category.isEmpty()) {
            response.put("details", result.getRight());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/outcome/financial-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getFinancialDetailByOutcome(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pair<OutcomeFinancialDetailDto, PaginationUtil<OutcomeFinancialDetailItemDto, OutcomeFinancialDetailItemDto>> result =
                financialService.getFinancialDetailByOutcome(category, page, size);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "OK");
        response.put("message", "Success retrieved financial detail");
        response.put("result", result.getLeft());

        // Tampilkan pagination hanya jika category dikirim
        if (category != null && !category.isEmpty()) {
            response.put("details", result.getRight());
        }

        return ResponseEntity.ok(response);

    }
}
