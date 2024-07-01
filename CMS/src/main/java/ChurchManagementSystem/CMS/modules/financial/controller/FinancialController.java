package ChurchManagementSystem.CMS.modules.financial.controller;

import ChurchManagementSystem.CMS.modules.financial.service.FinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/financial")
@CrossOrigin(origins = "http://localhost:4200")
public class FinancialController {
    @Autowired
    private FinancialService financialService;


    //Created Financial
//    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> createFinancial(
//            @RequestBody FinancialDto request
//            ){
//        try {
//            IncomeEntity income =  request.getTotalIncome();
//            OutcomeEntity outcome = request.getTotalOutcome();
//            FinancialEntity balance = financialService.calculateBalance(income, outcome);
//            ApiResponse<FinancialEntity> response = new ApiResponse<>(HttpStatus.CREATED, "Balance calculated successfull", balance);
//            return new ResponseEntity<>(response, response.getStatus());
//        }catch (CustomRequestException err){
//            return err.GlobalCustomRequestException(err.getMessage(), err.getStatus());
//        }
//    }


}
