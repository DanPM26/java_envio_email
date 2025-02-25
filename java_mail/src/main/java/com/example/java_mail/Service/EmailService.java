package com.example.java_mail.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("#{'${app.email.to}'.split(',')}")
    private List<String> toEmails;

    public void sendEmail(String subject, String message) throws MessagingException{
        MimeMessage mimeMessage= javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        // traer la dirección desde el properties
        helper.setTo(toEmails.toArray(new String[0]));
        helper.setSubject(subject);

        // Obtener la fecha y hora actual
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fechaHora = now.format(formatter);

        // Procesar el template con Thymeleaf
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("fechayHora", fechaHora);
        String htmlContent = templateEngine.process("email-template", context);

        helper.setText(htmlContent, true);

        javaMailSender.send(mimeMessage);
    }
}
