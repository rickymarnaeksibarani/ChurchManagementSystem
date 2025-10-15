//package ChurchManagementSystem.CMS.core.Exception;
//
//import ChurchManagementSystem.CMS.core.CustomResponse.ApiResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.multipart.MultipartException;
//import org.springframework.web.server.ResponseStatusException;
//import org.springframework.web.servlet.resource.NoResourceFoundException;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@ControllerAdvice
//@Slf4j
//public class GlobalExceptionHendler {
//
//    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHendler.class);
//
//
//    @ExceptionHandler(MultipartException.class)
//    public ResponseEntity<Map<String, Object>> handleMultipartException(MultipartException ex) {
//        // log full stack trace ke console atau file
//        logger.error("Multipart upload error", ex);
//
//        String message = ex.getMessage();
//        if (message != null && message.contains("no multipart boundary found")) {
//            // tangani khusus boundary error
//            return buildResponse("Invalid multipart request: boundary not found. Please send form-data correctly.", HttpStatus.BAD_REQUEST);
//        }
//
//        // tangani error multipart lainnya
//        return buildResponse("Multipart error: " + message, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
//        logger.error("ResponseStatusException: {}", ex.getReason(), ex);
//        return buildResponse(ex.getReason(), (HttpStatus) ex.getStatusCode());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
//        logger.error("Unhandled exception", ex);
//        return buildResponse("Internal server error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("status", status.value());
//        body.put("error", status.getReasonPhrase());
//        body.put("message", message);
//        body.put("timestamp", java.time.LocalDateTime.now());
//        return new ResponseEntity<>(body, status);
//    }
//
//    @ExceptionHandler(NoResourceFoundException.class)
//    public ResponseEntity<ApiResponse<Object>> handleNoResourceFound(NoResourceFoundException ex) {
//        ApiResponse<Object> response = new ApiResponse<>(
//                HttpStatus.NOT_FOUND,
//                "Endpoint not found: " + ex.getResourcePath(),
//                null
//        );
//        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {
//        ApiResponse<Object> response = new ApiResponse<>(
//                HttpStatus.INTERNAL_SERVER_ERROR,
//                ex.getMessage(),
//                null
//        );
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
