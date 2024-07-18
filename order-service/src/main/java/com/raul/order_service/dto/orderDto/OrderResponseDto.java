package com.raul.order_service.dto.orderDto;

import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


public record OrderResponseDto(
        Integer id,
        Integer appUserId,
        List<OrderProductsDto> products,
        BigDecimal totalPrice,
        String status,
        LocalDateTime createdAt
) { }
