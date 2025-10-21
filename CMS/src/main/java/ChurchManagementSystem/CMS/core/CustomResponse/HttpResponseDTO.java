package ChurchManagementSystem.CMS.core.CustomResponse;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@EqualsAndHashCode
public class HttpResponseDTO<T> {
    private T response;
    private Map<String, Object> headers = new HashMap<>();
    private String message;
    private LocalDateTime time;

    public HttpResponseDTO() {
        this.time = LocalDateTime.now();
    }

    public HttpResponseDTO(T _response) {
        this.response = _response;
        this.headers.put("status", HttpStatus.OK);
        this.time = LocalDateTime.now();
    }
    public HttpResponseDTO(T _response, HttpStatus status) {
        this.response = _response;
        this.headers.put("status", status);
        this.time = LocalDateTime.now();
    }

    public HttpResponseDTO(T _response, Map<String, Object> _headers) {
        this.response = _response;
        this.headers = _headers;
        this.time = LocalDateTime.now();
    }

    public ResponseEntity<HttpResponseDTO<T>> toResponse(HttpStatus status) {
        return new ResponseEntity<HttpResponseDTO<T>>(this, status);
    }

    public ResponseEntity<HttpResponseDTO<T>> toResponse(String _message) {
        this.message = _message;
        return new ResponseEntity<HttpResponseDTO<T>>(this, HttpStatus.OK);
    }

    public ResponseEntity<HttpResponseDTO<T>> toResponse(String _message, HttpStatus httpStatus) {
        this.message = _message;
        return new ResponseEntity<HttpResponseDTO<T>>(this, httpStatus);
    }

    public ResponseEntity<HttpResponseDTO<T>> toResponse() {
        return new ResponseEntity<HttpResponseDTO<T>>(this, HttpStatus.OK);
    }

    public HttpResponseDTO<T> setResponseHeaders(String key, Object value) {
        this.headers.put(key, value);
        return this;
    }
}
