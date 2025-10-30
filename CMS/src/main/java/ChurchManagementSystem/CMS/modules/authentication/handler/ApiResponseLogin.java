package ChurchManagementSystem.CMS.modules.authentication.handler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
@Data
@Builder
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseLogin <T>{
    private HttpStatusCode status;
    private String message;
    private T result;

    public ApiResponseLogin(HttpStatusCode status, String message, T result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }
}
