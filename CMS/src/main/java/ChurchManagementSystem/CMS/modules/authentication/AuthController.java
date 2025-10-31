package ChurchManagementSystem.CMS.modules.authentication;

import ChurchManagementSystem.CMS.modules.authentication.dto.AuthDto;
import ChurchManagementSystem.CMS.modules.authentication.dto.LogoutResponseDto;
import ChurchManagementSystem.CMS.modules.authentication.handler.ApiResponseLogin;
import ChurchManagementSystem.CMS.modules.authentication.handler.BlackListToken;
import ChurchManagementSystem.CMS.modules.authentication.repository.User;
import ChurchManagementSystem.CMS.modules.authentication.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(authService.verifyEmail(token));
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponseLogin<Map<String, String>>> login(
            @RequestParam("email") String email,
            @RequestParam("password") String password) {

        String token = authService.login(email, password);

        Map<String, String> result = new HashMap<>();
        result.put("bearer_token", token);

        ApiResponseLogin<Map<String, String>> response = ApiResponseLogin.<Map<String, String>>builder()
                .status(HttpStatus.OK)
                .message("Login successful")
                .result(result)
                .build();

        return ResponseEntity.ok(response);
    }


    @PostMapping(value = "/resend", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resendVerification(@RequestParam String email) {
        return ResponseEntity.ok(authService.resendVerification(email));
    }

    @PostMapping(value = "/forgot", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return ResponseEntity.ok(authService.forgotPassword(email));
    }

    @PostMapping(value = "/reset", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        return ResponseEntity.ok(authService.resetPassword(token, newPassword));
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
