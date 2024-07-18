package com.raul.payment_service.dto.paymentDto;


import com.raul.payment_service.dto.appUserDto.AppUserDto;

import java.math.BigDecimal;

public record PaymentRequestDto(
        String token,
        BigDecimal totalPrice,
        String currency,
        AppUserDto appUser
) { }
