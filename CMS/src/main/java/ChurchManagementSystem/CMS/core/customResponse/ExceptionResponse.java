package ChurchManagementSystem.CMS.core.customResponse;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ExceptionResponse {
    private final HttpStatus status;
    private final String message;

    public ExceptionResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}