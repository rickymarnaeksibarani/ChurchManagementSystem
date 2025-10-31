package ChurchManagementSystem.CMS.modules.authentication;

import ChurchManagementSystem.CMS.core.customResponse.ApiResponse;
import ChurchManagementSystem.CMS.modules.authentication.dto.AuthDto;
import ChurchManagementSystem.CMS.modules.authentication.dto.LogoutResponseDto;
import ChurchManagementSystem.CMS.modules.authentication.handler.ApiResponseLogin;
import ChurchManagementSystem.CMS.modules.authentication.handler.BlackListToken;
import ChurchManagementSystem.CMS.modules.authentication.repository.User;
import ChurchManagementSystem.CMS.modules.authentication.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final BlackListToken blackListToken;
    private final User userRepository;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> register(@RequestBody AuthDto authDto) {
        String message = authService.register(authDto.getEmail(), authDto.getPassword());

        Map<String, String> response = new HashMap<>();
        response.put("message", message);

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/verify", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Object>> verifyEmail(@RequestParam String token) {
        try {
            String message = authService.verifyEmail(token);
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(true)
                    .message(message)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Map<String, String>>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        try {
            String token = authService.login(email, password);

            Map<String, String> result = new HashMap<>();
            result.put("bearer_token", token);

            return ResponseEntity.ok(ApiResponseLogin.<Map<String, String>>builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(true)
                    .message("Login successful")
                    .result(result)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseLogin.<Map<String, String>>builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }



    @PostMapping(value = "/resend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Object>> resendVerification(@RequestParam String email) {
        try {
            String message = authService.resendVerification(email);
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(true)
                    .message(message)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }


    @PostMapping(value = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Object>> forgotPassword(@RequestParam String email) {
        try {
            String message = authService.forgotPassword(email);
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(true)
                    .message(message)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }


    @PostMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Object>> resetPassword(
            @RequestParam String token,
            @RequestParam String newPassword) {
        try {
            String message = authService.resetPassword(token, newPassword);
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(true)
                    .message(message)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponseLogin.builder()
                    .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }


    @PostMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<LogoutResponseDto>> logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");


        String token = authHeader.substring(7);
        blackListToken.add(token);

        return ResponseEntity.ok(
                ApiResponseLogin.<LogoutResponseDto>builder()
                        .status(HttpStatus.OK)
                        .message("User logged out successfully")
                        .result(new LogoutResponseDto(token))
                        .build()
        );
    }


}
