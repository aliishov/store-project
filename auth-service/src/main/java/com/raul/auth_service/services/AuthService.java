package com.raul.auth_service.services;

import com.raul.auth_service.dto.AuthenticationRequest;
import com.raul.auth_service.dto.AuthenticationResponse;
import com.raul.auth_service.dto.RegistrationRequest;
import com.raul.auth_service.models.Role;
import com.raul.auth_service.models.User;
import com.raul.auth_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<AuthenticationResponse> register(RegistrationRequest request){
        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(AuthenticationResponse
                                                .builder()
                                                .token(jwtToken)
                                                .build(), HttpStatus.CREATED);

    }

    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + request.getEmail() + " not found"));

        var jwtToken = jwtService.generateToken(user);

        return new ResponseEntity<>(AuthenticationResponse
                                                .builder()
                                                .token(jwtToken)
                                                .build(), HttpStatus.OK);
    }
}
