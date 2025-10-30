package ChurchManagementSystem.CMS.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidatorExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidatorExceptionDTO> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String errorMessage = fieldName + ": " + error.getDefaultMessage();

            errors.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(errorMessage);
        });

        ValidatorExceptionDTO response = new ValidatorExceptionDTO(HttpStatus.BAD_REQUEST, "Validation failed", errors);
        return new ResponseEntity<>(response, response.getStatus());
    }

}
