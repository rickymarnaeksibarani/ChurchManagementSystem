package ChurchManagementSystem.CMS.modules.authentication.service;

import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
import ChurchManagementSystem.CMS.core.utils.JwtUtil;
import ChurchManagementSystem.CMS.modules.authentication.entity.UserEntity;
import ChurchManagementSystem.CMS.modules.authentication.repository.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final User userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public String register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent())
            throw new CustomRequestException("Email already registered", HttpStatus.CONFLICT);

        String token = UUID.randomUUID().toString();
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setVerificationToken(token);
        user.setEnabled(false);
        userRepository.save(user);

        log.info("ini link verify register: http://localhost:8080/api/auth/verify?token=" + token);

        return "User registered successfully. Please check your email for verification link.";
    }

    public String verifyEmail(String token) {
        UserEntity user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new CustomRequestException("Invalid token", HttpStatus.BAD_REQUEST));
        user.setEnabled(true);
        user.setVerificationToken(null);
        userRepository.save(user);
        return "Email verified successfully.";
    }

    public String login(String email, String password) {
        List<UserEntity> all = userRepository.findAll();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));

        if (!user.isEnabled()) throw new CustomRequestException("Account not verified", HttpStatus.FORBIDDEN);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CustomRequestException("Invalid password", HttpStatus.BAD_REQUEST);

        return jwtUtil.generateToken(email);
    }

    public String resendVerification(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));
        if (user.isEnabled()){
            throw new CustomRequestException("Already verified", HttpStatus.CONFLICT);
        }
        String newToken = UUID.randomUUID().toString();
        user.setVerificationToken(newToken);
        userRepository.save(user);
        log.info("ini link resend verify: http://localhost:8080/api/auth/verify?token=" + newToken);

        return "Verification email resent.";
    }

    public String forgotPassword(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));
        String resetToken = UUID.randomUUID().toString();
        user.setResetToken(resetToken);
        userRepository.save(user);
        log.info("ini link token forgot password: "+ resetToken);
        return "Password reset email sent.";
    }

    public String resetPassword(String token, String newPassword) {
        UserEntity user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new CustomRequestException("Invalid token", HttpStatus.BAD_REQUEST));
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        userRepository.save(user);
        return "Password successfully reset.";
    }

}

