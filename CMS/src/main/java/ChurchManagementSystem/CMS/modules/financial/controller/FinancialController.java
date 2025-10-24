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
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeRequestDto;
import ChurchManagementSystem.CMS.modules.financial.dto.outcome.OutcomeResponeDto;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
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

    @GetMapping(value = "/income/financial-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getFinancialDetailByIncome(
            @ModelAttribute IncomeRequestDto requestDto

    ) {

        Pair<IncomeFinancialDetailDto, PaginationUtil<IncomeFinancialDetailItemDto, IncomeFinancialDetailItemDto>> result =
                financialService.getFinancialDetailByIncome(requestDto);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "OK");
        response.put("message", "Success retrieved financial detail");
        response.put("result", result.getLeft());

        // Tampilkan pagination hanya jika category dikirim
        if (requestDto.getCategory() != null && !requestDto.getCategory().isEmpty()) {
            response.put("details", result.getRight());
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/outcome/financial-detail", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getFinancialDetailByOutcome(
            @ModelAttribute OutcomeRequestDto requestDto
            ) {

        Pair<OutcomeFinancialDetailDto, PaginationUtil<OutcomeFinancialDetailItemDto, OutcomeFinancialDetailItemDto>> result =
                financialService.getFinancialDetailByOutcome(requestDto);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "OK");
        response.put("message", "Success retrieved financial detail");
        response.put("result", result.getLeft());

        // Tampilkan pagination hanya jika category dikirim
        if (requestDto.getCategory() != null && !requestDto.getCategory().isEmpty()) {
            response.put("details", result.getRight());
        }

        return ResponseEntity.ok(response);

    }

    @GetMapping("/income/total")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTotalIncome(
            @RequestParam (required = false) String year,
            @RequestParam(required = false) String month) {

        BigDecimal totalIncome = financialService.getTotalIncomeByYearAndMonth(year, month);

        String message = (month == null || month.isBlank())
                ? "Successfully retrieved total income for all months in year " + year
                : "Successfully retrieved total income for year " + year + ", month " + month;

        Map<String, Object> data = new HashMap<>();
        data.put("year", year);
        data.put("month", month);
        data.put("totalIncome", totalIncome);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, message, data));
    }


    @GetMapping("/outcome/total")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTotalOutcome(
            @RequestParam (required = false) String year,
            @RequestParam(required = false) String month) {

        BigDecimal totalOutcome = financialService.getTotalOutcomeByYearAndMonth(year, month);

        String message = (month == null || month.isBlank())
                ? "Successfully retrieved total outcome for all months in year " + year
                : "Successfully retrieved total outcome for year " + year + ", month " + month;

        Map<String, Object> data = new HashMap<>();
        data.put("year", year);
        data.put("month", month);
        data.put("totalOutcome", totalOutcome);

        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, message, data));
    }
}
