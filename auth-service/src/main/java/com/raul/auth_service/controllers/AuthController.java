package com.raul.auth_service.controllers;

import com.raul.auth_service.dto.AuthenticationRequest;
import com.raul.auth_service.dto.AuthenticationResponse;
import com.raul.auth_service.dto.RegistrationRequest;
import com.raul.auth_service.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegistrationRequest request) {
        return service.register(request);
    }

    @PostMapping("authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        return service.authenticate(request);
    }
}
