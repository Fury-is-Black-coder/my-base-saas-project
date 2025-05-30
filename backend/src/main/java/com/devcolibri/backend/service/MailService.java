package com.devcolibri.backend.service;

import org.springframework.mail.javamail.JavaMailSender;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendActivationEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Aktywacja konta w DevColibriStudio");
            helper.setText("Dzień dobry,\nKliknij w link, aby aktywować konto: http://localhost:8080/api/auth/activate?code=" + code, false);
            helper.setFrom("no-reply@devcolibri.pl", "DevColibriStudio");

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Błąd podczas wysyłania maila", e);
        }
    }
}
