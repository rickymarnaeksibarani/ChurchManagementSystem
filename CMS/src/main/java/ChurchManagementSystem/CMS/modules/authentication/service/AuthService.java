package ChurchManagementSystem.CMS.modules.authentication.service;

import ChurchManagementSystem.CMS.core.enums.Role;
import ChurchManagementSystem.CMS.core.exception.CustomRequestException;
//import ChurchManagementSystem.CMS.core.mail.EmailService;
import ChurchManagementSystem.CMS.core.utils.JwtUtil;
import ChurchManagementSystem.CMS.modules.authentication.dto.AdminChangePasswordDto;
import ChurchManagementSystem.CMS.modules.authentication.dto.ChangePasswordDto;
import ChurchManagementSystem.CMS.modules.authentication.dto.LoginResponseDto;
import ChurchManagementSystem.CMS.modules.authentication.dto.LogoutResponseDto;
import ChurchManagementSystem.CMS.modules.authentication.entity.UserEntity;
import ChurchManagementSystem.CMS.modules.authentication.handler.BlackListToken;
import ChurchManagementSystem.CMS.modules.authentication.repository.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
//    private final EmailService emailService;
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

//        String token = UUID.randomUUID().toString();
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
//        user.setVerificationToken(token);
        user.setEnabled(true);
        user.setRole(Role.ADMIN);
        userRepository.save(user);

//        emailService.sendVerificationEmail(email, token);

//        log.info("ini link verify register: http://localhost:8080/api/auth/verify?token=" + token);

        return "User registered successfully";
    }

    public String registerUser(String email, String password, String role) {
        if (userRepository.findByEmail(email).isPresent())
            throw new CustomRequestException("Email already registered", HttpStatus.CONFLICT);

        if (!StringUtils.hasText(email))
            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);

        if (!StringUtils.hasText(password))
            throw new CustomRequestException("Password cannot be blank", HttpStatus.BAD_REQUEST);

//        String token = UUID.randomUUID().toString();

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
//        user.setVerificationToken(token);
        user.setEnabled(true);

//        Role selectedRole = Role.USER;
//        if ("ADMIN".equalsIgnoreCase(role)){
//            selectedRole = Role.ADMIN;
//        }
        user.setRole(Role.USER);

        userRepository.save(user);
//        emailService.sendVerificationEmail(email, token);

//        log.info("ini link verify register: http://localhost:8080/api/auth/verify?token=" + token);

        return "User registered successfully";
    }


//    public String verifyEmail(String token) {
//        UserEntity user = userRepository.findByVerificationToken(token)
//                .orElseThrow(() -> new CustomRequestException("Invalid verification token", HttpStatus.BAD_REQUEST)) ;
//        user.setEnabled(true);
//        user.setVerificationToken(null);
//        userRepository.save(user);
//        return "Email successfully verified, please log in again";
//    }

    public LoginResponseDto login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));

//        if (!user.isEnabled())
//            throw new CustomRequestException("Account not verified", HttpStatus.FORBIDDEN);

        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CustomRequestException("Invalid password", HttpStatus.BAD_REQUEST);

//        if (!user.getRole().equals(Role.ADMIN)){
//            throw new CustomRequestException("Access denied: only ADMIN role can login here", HttpStatus.FORBIDDEN);
//        }
        String token =  jwtUtil.generateTokenWithRole(email, user.getRole().name());
        return new LoginResponseDto(token, user.getRole().name());


    }

    //todo: login as user (view only)
//    public LoginResponseDto loginAsUser(String email, String password){
//        if (!StringUtils.hasText(email)){
//            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
//
//        }
//        if (!StringUtils.hasText(password)){
//            throw new CustomRequestException("Password cannot be blank", HttpStatus.BAD_REQUEST);
//
//        }
//
//        UserEntity user = userRepository.findByEmail(email)
//                .orElseThrow(()-> new CustomRequestException("Email not found", HttpStatus.NOT_FOUND));
//
//        if (!user.isEnabled()){
//            throw new CustomRequestException("Account not verified", HttpStatus.FORBIDDEN);
//        }
//
//        if (!passwordEncoder.matches(password, user.getPassword())){
//            throw new CustomRequestException("Invalid Password", HttpStatus.BAD_REQUEST);
//        }
//
//        if (!user.getRole().equals(Role.USER)){
//            throw new CustomRequestException("Access denied: only USER role can login here", HttpStatus.FORBIDDEN);
//        }
//        String token =  jwtUtil.generateTokenWithRole(email, user.getRole().name());
//        return new  LoginResponseDto(token, user.getRole().name());
//    }

//    public String resendVerification(String email) {
//        if (!StringUtils.hasText(email)){
//            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
//        }
//        UserEntity user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));
//        if (user.isEnabled()){
//            throw new CustomRequestException("Already verified", HttpStatus.CONFLICT);
//        }
//        String newToken = UUID.randomUUID().toString();
//        user.setVerificationToken(newToken);
//        userRepository.save(user);
//        emailService.sendVerificationEmail(email, newToken);
//        log.info("ini link resend verify: http://localhost:8080/api/auth/verify?token=" + newToken);
//
//        return "Verification email resent.";
//    }

//    public String forgotPassword(String email) {
//        if (!StringUtils.hasText(email)){
//            throw new CustomRequestException("Email cannot be blank", HttpStatus.BAD_REQUEST);
//        }
//        UserEntity user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new CustomRequestException("User not found", HttpStatus.NOT_FOUND));
//        if(!user.isEnabled()){
//            throw new CustomRequestException("Your account has not been verified. Please verify your email first.", HttpStatus.FORBIDDEN);
//        }
//        String resetToken = UUID.randomUUID().toString();
//        user.setResetToken(resetToken);
//        userRepository.save(user);
//        emailService.sendResetPasswordEmail(email, resetToken);
//        log.info("ini link token forgot password: "+ resetToken);
//        return "Email sent, please check";
//    }

//    public String resetPassword(String token, String newPassword) {
//
//        if (!StringUtils.hasText(newPassword)){
//            throw new CustomRequestException("Password cannot be blank", HttpStatus.BAD_REQUEST);
//        }
//        UserEntity user = userRepository.findByResetToken(token)
//                .orElseThrow(() -> new CustomRequestException("Invalid token", HttpStatus.BAD_REQUEST));
//        user.setPassword(passwordEncoder.encode(newPassword));
//        user.setResetToken(null);
//        userRepository.save(user);
//        return "Password successfully reset.";
//    }

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

//    todo: reset password (admin & user): token nya di hilangin, pakai email aja penanda nya (admin boleh ubah password si user)

    public String resetPassword( ChangePasswordDto changePasswordDto) {

        String loggedInEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity user = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new CustomRequestException(
                                "User not found",
                                HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword())) {

            throw new CustomRequestException("Old password is incorrect", HttpStatus.BAD_REQUEST);
        }

        if (passwordEncoder.matches(changePasswordDto.getNewPassword(), user.getPassword())) {

            throw new CustomRequestException("New password must be different from old password", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));

        userRepository.save(user);

        return "Password successfully changed";
    }

    public String adminChangeUserPassword(AdminChangePasswordDto dto) {

        // ambil admin yang login
        String loggedInEmail = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        UserEntity admin = userRepository.findByEmail(loggedInEmail)
                .orElseThrow(() ->
                        new CustomRequestException(
                                "Admin not found",
                                HttpStatus.NOT_FOUND));

        // pastikan yang login benar ADMIN
        if (admin.getRole() != Role.ADMIN) {
            throw new CustomRequestException(
                    "Access denied: only ADMIN can change other user password",
                    HttpStatus.FORBIDDEN);
        }

        // cari user target
        UserEntity targetUser = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new CustomRequestException(
                                "User not found",
                                HttpStatus.NOT_FOUND));

        // optional: admin tidak boleh ubah password admin lain (kalau mau dibatasi)
         if (targetUser.getRole() == Role.ADMIN) {
             throw new CustomRequestException("Cannot change another admin password", HttpStatus.FORBIDDEN);
         }

        targetUser.setPassword(
                passwordEncoder.encode(dto.getNewPassword())
        );

        userRepository.save(targetUser);

        return "User password changed successfully by admin";
    }

}

// alurnya: endpoint untuk change password USER & ADMIN adalah: /api/auth/change-password (dapat dilakukan ketika si USER atau si ADMIN login)
// kemudian kalo ADMIN ingin ubah password si user gunakan endpoint: /admin/change-password
