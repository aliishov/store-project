package com.raul.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record AppUserRequestDto(
        Integer id,

        @NotNull(message = "First name should not be empty")
        String firstName,

        @NotNull(message = "Last name should not be empty")
        String lastName,

        @NotNull(message = "Email should not be empty")
        @Email(message = "Email not valid")
        String email
) { }
