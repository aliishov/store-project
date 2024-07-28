package com.raul.notification_service.controller;

import com.raul.notification_service.dto.EmailDto;
import com.raul.notification_service.services.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody EmailDto emailDto) throws MessagingException {
        return emailService.processEmailMessage(emailDto);
    }
}
