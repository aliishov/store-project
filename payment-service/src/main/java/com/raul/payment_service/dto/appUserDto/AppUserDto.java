package com.raul.payment_service.dto.appUserDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public record AppUserDto(
        Integer id,

        @NotNull(message = "First name should not be empty")
        String firstName,

        @NotNull(message = "Last name should not be empty")
        String lastName,

        @NotNull(message = "Email should not be empty")
        @Email(message = "Email is not valid")
        String email
) { }
