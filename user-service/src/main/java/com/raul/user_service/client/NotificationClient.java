package com.raul.user_service.client;

import com.raul.user_service.dto.emailDto.EmailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "notification-service", url = "${notification.service.url}")
public interface NotificationClient {

    @PostMapping("/send")
    ResponseEntity<?> send(@RequestBody EmailDto emailDto);
}
