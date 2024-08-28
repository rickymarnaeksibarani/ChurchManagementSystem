package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.standard.Media;
import java.math.BigDecimal;
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
    public List<IncomeEntity> getIncomeByMonth(@RequestParam int month, @RequestParam int year) {
        return financialService.getIncomeByMonth(month, year);
    }

    //Getting total income by month
    @GetMapping(value = "/income/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getTotalIncomeByMonth(@RequestParam int month, @RequestParam int year) {
        return financialService.getTotalIncomeByMonth(month, year);
    }

    //Getting total outcome by month
    @GetMapping(value = "/outcome/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public BigDecimal getTotalOutcomeByMonth(@RequestParam int month, @RequestParam int year){
        return financialService.getTotalOutcomeByMonth(month, year);
    }
    //Getting Outcome summary by mounth
    @GetMapping(value = "/outcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<OutcomeEntity> getOutcomeByMonth(@RequestParam int month, @RequestParam int year){
        return financialService.getOutcomeByMonth(month, year);
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
