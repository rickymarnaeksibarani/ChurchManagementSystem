package ChurchManagementSystem.CMS.modules.authentication;

import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
import ChurchManagementSystem.CMS.modules.authentication.dto.AuthDto;
import ChurchManagementSystem.CMS.modules.authentication.dto.LogoutResponseDto;
import ChurchManagementSystem.CMS.modules.authentication.handler.ApiResponseLogin;
import ChurchManagementSystem.CMS.modules.authentication.handler.BlackListToken;
import ChurchManagementSystem.CMS.modules.authentication.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Map<String, String>>> register(@ModelAttribute AuthDto authDto) {
        try {
            String message = authService.register(authDto.getEmail(), authDto.getPassword());

            Map<String, String> result = new HashMap<>();
            result.put("email", authDto.getEmail());

            return ResponseEntity.ok(
                    ApiResponseLogin.<Map<String, String>>builder()
                            .status(HttpStatus.OK)
                            .success(true)
                            .message(message)
                            .result(result)
                            .build()
            );

        } catch (CustomRequestException e) {
            return ResponseEntity.status(e.getStatus()).body(
                    ApiResponseLogin.<Map<String, String>>builder()
                            .status(e.getStatus())
                            .success(false)
                            .message(e.getMessage())
                            .build()
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    ApiResponseLogin.<Map<String, String>>builder()
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .success(false)
                            .message("Internal server error: " + e.getMessage())
                            .build()
            );
        }
    }


    @GetMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
       return ResponseEntity.ok(authService.verifyEmail(token));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Object>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        try {
            String token = authService.login(email, password);

            Map<String, String> result = new HashMap<>();
            result.put("bearer_token", token);

            return ResponseEntity.ok(ApiResponseLogin.builder()
                            .status(HttpStatus.OK)
                            .success(true)
                            .message("User Login Successfully")
                            .result(result)
                            .build()
                    );
            }catch (CustomRequestException e){
                return ResponseEntity.status(e.getStatus()).body(
                        ApiResponseLogin.builder()
                                .status(e.getStatus())
                                .success(false)
                                .message(e.getMessage())
                                .build()
                );
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
        } catch (CustomRequestException e) {
            return ResponseEntity.status(e.getStatus()).body(
                    ApiResponseLogin.builder()
                            .status(e.getStatus())
                            .success(false)
                            .message(e.getMessage())
                            .build()
            );
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
        } catch (CustomRequestException e) {
            return ResponseEntity.status(e.getStatus()).body(
                    ApiResponseLogin.builder()
                            .status(e.getStatus())
                            .success(false)
                            .message(e.getMessage())
                            .build()
            );
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
        } catch (CustomRequestException e) {
            return ResponseEntity.status(e.getStatus()).body(
                    ApiResponseLogin.builder()
                            .status(e.getStatus())
                            .success(false)
                            .message(e.getMessage())
                            .build()
            );
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
