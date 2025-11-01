package ChurchManagementSystem.CMS.modules.authentication.service;

import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.mail.EmailService;
import ChurchManagementSystem.CMS.core.utils.JwtUtil;
import ChurchManagementSystem.CMS.modules.authentication.dto.LogoutResponseDto;
import ChurchManagementSystem.CMS.modules.authentication.entity.UserEntity;
import ChurchManagementSystem.CMS.modules.authentication.handler.BlackListToken;
import ChurchManagementSystem.CMS.modules.authentication.repository.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final User userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final BlackListToken blackListToken;

    public String register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent())
            throw new CustomRequestException("Email already registered", HttpStatus.CONFLICT);
        if (!StringUtils.hasText(email)){
            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(password)){
            throw new CustomRequestException("Password cannot be blank", HttpStatus.BAD_REQUEST);
        }

        String token = UUID.randomUUID().toString();
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerificationToken(token);
        user.setEnabled(false);
        userRepository.save(user);

        emailService.sendVerificationEmail(email, token);

        log.info("ini link verify register: http://localhost:8080/api/auth/verify?token=" + token);

        return "User registered successfully. Please check your email for verification link.";
    }

    public String verifyEmail(String token) {
        UserEntity user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new CustomRequestException("Invalid verification token", HttpStatus.BAD_REQUEST)) ;
        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        return "EMAIL BERHASIL DI VERIFIKASI, SILAHKAN LOGIN KEMBALI";
    }

    public String login(String email, String password) {
        if (!StringUtils.hasText(email)){
            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
        }
        if (!StringUtils.hasText(password)){
            throw new CustomRequestException("Password cannot be blank", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));

        if (!user.isEnabled()) throw new CustomRequestException("Account not verified", HttpStatus.FORBIDDEN);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CustomRequestException("Invalid password", HttpStatus.BAD_REQUEST);

        return jwtUtil.generateToken(email);
    }

    public String resendVerification(String email) {
        if (!StringUtils.hasText(email)){
            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));
        if (user.isEnabled()){
            throw new CustomRequestException("Already verified", HttpStatus.CONFLICT);
        }
        String newToken = UUID.randomUUID().toString();
        user.setVerificationToken(newToken);
        userRepository.save(user);
        emailService.sendVerificationEmail(email, newToken);
        log.info("ini link resend verify: http://localhost:8080/api/auth/verify?token=" + newToken);

        return "Verification email resent.";
    }

    public String forgotPassword(String email) {
        if (!StringUtils.hasText(email)){
            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));
        if(!user.isEnabled()){
            throw new CustomRequestException("Akun belum diverifikasi. Silahkan verifikasi email anda terlebih dahulu", HttpStatus.FORBIDDEN);
        }
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);
        emailService.sendResetPasswordEmail(email, resetToken);
        log.info("ini link token forgot password: "+ resetToken);
        return "Password reset email sent.";
    }

    public String resetPassword(String token, String newPassword) {

        if (!StringUtils.hasText(newPassword)){
            throw new CustomRequestException("Password cannot be blank", HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new CustomRequestException("Invalid token", HttpStatus.BAD_REQUEST));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
        return "Password successfully reset.";
    }

    public LogoutResponseDto logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new CustomRequestException("Missing or invalid Authorization header", HttpStatus.FORBIDDEN);
        }

        String token = authHeader.substring(7);

        if (!jwtUtil.validateToken(token)) {
            throw new CustomRequestException("Invalid or expired token", HttpStatus.BAD_REQUEST);
        }

        blackListToken.add(token);
        return new LogoutResponseDto(token);
    }

}

