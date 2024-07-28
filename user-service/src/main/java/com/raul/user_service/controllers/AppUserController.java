package com.raul.user_service.controllers;

import com.raul.user_service.dto.AppUserRequestDto;
import com.raul.user_service.dto.AppUserResponseDto;
import com.raul.user_service.services.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @PostMapping("/create")
    public ResponseEntity<AppUserResponseDto> createAppUser(@RequestBody @Valid AppUserRequestDto request) {
        return appUserService.createAppUser(request);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateAppUser(@RequestBody @Valid AppUserRequestDto request) {
        return appUserService.updateAppUser(request);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppUserResponseDto>> getAll() {
        return appUserService.getAll();
    }

    @GetMapping("/{appUserId}")
    public ResponseEntity<AppUserResponseDto> getAppUserById(@PathVariable Integer appUserId) {
        return appUserService.getAppUserById(appUserId);
    }

    @DeleteMapping("/del/{appUserId}")
    public ResponseEntity<Void> deleteAppUser(@PathVariable Integer appUserId) {
        return appUserService.deleteAppUser(appUserId);
    }
}
