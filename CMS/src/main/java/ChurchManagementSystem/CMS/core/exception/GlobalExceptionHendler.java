package ChurchManagementSystem.CMS.core.exception;

import ChurchManagementSystem.CMS.core.customResponse.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

@RestControllerAdvice
public class GlobalExceptionHendler {
    @ExceptionHandler(CustomRequestException.class)
    public ResponseEntity<ExceptionResponse> handleCustomRequestException(CustomRequestException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ExceptionResponse(ex.getStatus(), ex.getMessage()));
    }

    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleMultipartException(MultipartException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Invalid multipart request: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {
        ex.printStackTrace(); // optional: untuk debugging di console
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"));
    }
}
