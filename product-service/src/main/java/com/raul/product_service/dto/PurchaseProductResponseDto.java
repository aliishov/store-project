package com.raul.product_service.dto;

import java.math.BigDecimal;

public record PurchaseProductResponseDto(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) { }
