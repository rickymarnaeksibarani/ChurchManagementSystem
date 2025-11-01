package ChurchManagementSystem.CMS.core.mail;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final SpringTemplateEngine springTemplateEngine;
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;
    @Value("${baseUrl}")
    private String baseUrl;

    private void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }

    public void sendVerificationEmail(String toEmail, String token) {
        String subject = "Verify your email address";
        String verificationUrl = baseUrl + "api/auth/verify?token=" + token;

        Context context = new Context();
        context.setVariable("verificationUrl", verificationUrl);

        String body = springTemplateEngine.process("email-verification", context);

        sendHtmlEmail(toEmail, subject, body);
    }

    public void sendResetPasswordEmail(String toEmail, String token) {
        String subject = "Reset Password Akun Anda";
        String resetUrl = baseUrl + "api/auth/reset-password?token=" + token;
        Context context = new Context();
        context.setVariable("resetUrl", resetUrl);
        String body = springTemplateEngine.process("password-reset", context);

        sendHtmlEmail(toEmail, subject, body);
    }

    private void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

}
