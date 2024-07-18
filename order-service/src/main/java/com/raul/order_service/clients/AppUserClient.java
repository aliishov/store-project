package com.raul.order_service.clients;

import com.raul.order_service.dto.appUserDto.AppUserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface AppUserClient {

    @GetMapping("/{appUserId}")
    ResponseEntity<AppUserResponseDto> getAppUserById(@PathVariable Integer appUserId);
}
