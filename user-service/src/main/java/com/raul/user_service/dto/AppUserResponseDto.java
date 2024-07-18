package com.raul.user_service.dto;

public record AppUserResponseDto(
        Integer id,
        String firstName,
        String lastName,
        String email
) { }
