package com.raul.order_service.dto.appUserDto;

public record AppUserResponseDto(
        Integer id,
        String firstName,
        String lastName,
        String email
) { }
