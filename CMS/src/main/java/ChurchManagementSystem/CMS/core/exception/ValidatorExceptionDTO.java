package ChurchManagementSystem.CMS.core.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ValidatorExceptionDTO {
    private HttpStatus status;
    private String message;
    private Map<String, List<String>> errors;

    public ValidatorExceptionDTO(HttpStatus status, String message, Map<String, List<String>> errors) {
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
