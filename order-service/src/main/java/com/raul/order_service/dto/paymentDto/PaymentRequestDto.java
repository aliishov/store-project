package com.raul.order_service.dto.paymentDto;


import com.raul.order_service.dto.appUserDto.AppUserResponseDto;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentRequestDto(
        @NotNull
        String token,

        BigDecimal totalPrice,

        @NotNull
        String currency,

        AppUserResponseDto appUser
) { }
