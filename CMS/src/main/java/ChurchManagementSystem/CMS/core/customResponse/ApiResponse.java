package ChurchManagementSystem.CMS.core.customResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatusCode;

@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private HttpStatusCode status;
    private String message; //status
    private T result; //respon

    public ApiResponse(HttpStatusCode status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}