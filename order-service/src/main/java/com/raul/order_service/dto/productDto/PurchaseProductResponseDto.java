package com.raul.order_service.dto.productDto;

import java.math.BigDecimal;

public record PurchaseProductResponseDto(
        Integer productId,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) { }
