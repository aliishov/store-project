package com.raul.order_service.dto.orderDto;

import java.math.BigDecimal;

public record OrderProductsDto(
        Integer productId,
        String name,
        Integer quantity,
        BigDecimal price
) { }
