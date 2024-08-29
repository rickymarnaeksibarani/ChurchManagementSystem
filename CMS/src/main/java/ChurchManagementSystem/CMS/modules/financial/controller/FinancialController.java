package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.core.utils.PaginationUtil;
import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //Getting Income summary by month
    @GetMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
    public PaginationUtil<IncomeEntity, IncomeEntity> getIncomeByMonth(@RequestParam int month,
                                                                       @RequestParam int year,
                                                                       @RequestParam(defaultValue = "1")int page,
                                                                       @RequestParam(defaultValue = "10")int size) {
        return financialService.getIncomeByMonth(month, year, page, size);
    }

    //Getting Outcome summary by mounth
    @GetMapping(value = "/outcome", produces = MediaType.APPLICATION_JSON_VALUE)
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

    //Create Income
    @PostMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeEntity addIncome(@RequestBody IncomeEntity income){
        return financialService.saveIncome(income);
    }

    //Create Outcome
    @PostMapping(value = "/outcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutcomeEntity addOutcome(@RequestBody OutcomeEntity outcome){
        return financialService.saveOutcome(outcome);
    }

    @PutMapping(value = "/income/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeEntity updateIncome(@PathVariable Long id, @RequestBody IncomeEntity income) {
        income.setIdIncome(id);
        return financialService.updateIncome(income);
    }

    @PutMapping(value = "/outcome/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutcomeEntity updateOutcome(@PathVariable Long id, @RequestBody OutcomeEntity outcome){
        outcome.setIdOutcome(id);
        return financialService.updateOutcome(outcome);
    }
}
