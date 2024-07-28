package com.raul.notification_service.services;

import com.raul.notification_service.dto.EmailDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    @Value("${application.mail.sent.from}")
    private String fromUsr;

    @RabbitListener(queues = "email_queue")
    public ResponseEntity<?> processEmailMessage(EmailDto emailDto) throws MessagingException{
        String to = emailDto.getTo();
        String subject = emailDto.getSubject();
        String body = emailDto.getBody();
        sendEmail(to, subject, body);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void sendEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setFrom(fromUsr);
        helper.setSubject(subject);
        helper.setText(body, true);
        emailSender.send(message);
    }

}
