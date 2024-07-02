package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.modules.financial.entities.FinancialEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.IncomeEntity;
import ChurchManagementSystem.CMS.modules.financial.entities.OutcomeEntity;
import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/financial")
@CrossOrigin(origins = "http://localhost:4200")
public class FinancialController {
    @Autowired
    private FinancialService financialService;

    @GetMapping(value = "/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    public FinancialEntity getBalance(){
        return financialService.calculateBalance();
    }

    @PostMapping(value = "/income", produces = MediaType.APPLICATION_JSON_VALUE)
    public IncomeEntity addIncome(@RequestBody IncomeEntity income){
        return financialService.saveIncome(income);
    }

    @PostMapping(value = "/outcome", produces = MediaType.APPLICATION_JSON_VALUE)
    public OutcomeEntity addOutcome(@RequestBody OutcomeEntity outcome){
        return financialService.saveOutcome(outcome);
    }

}
