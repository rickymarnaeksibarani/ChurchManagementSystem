package ChurchManagementSystem.CMS.core.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

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
        String verificationUrl = "http://localhost:8080/api/auth/verify?token=" + token;

        String body = """
                Shalom,
                
                Terima kasih telah mendaftar di Sistem Informasi Gereja.
                Untuk menyelesaikan proses pendaftaran dan mengaktifkan akun Anda,
                silakan verifikasi alamat email Anda dengan mengklik tautan berikut:

                %s

                Jika Anda tidak merasa melakukan pendaftaran ini, abaikan pesan ini.

                Hormat kami,
                Tim Sistem Informasi Gereja
                """.formatted(verificationUrl);

       sendEmail(toEmail, subject, body);
    }

    public void sendResetPasswordEmail(String toEmail, String token) {
        String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;
        String subject = "🔒 Reset Password Akun Anda";
        String body = """
                Shalom,
                
                Kami menerima permintaan untuk mengatur ulang kata sandi akun Anda.
                Silakan klik tautan di bawah ini untuk membuat kata sandi baru:

                %s

                Jika Anda tidak merasa melakukan permintaan ini, abaikan pesan ini, atau hubungi staff kami.

                Hormat kami,
                Tim Sistem Informasi Gereja
                """.formatted(resetUrl);

        sendEmail(toEmail, subject, body);
    }

}
