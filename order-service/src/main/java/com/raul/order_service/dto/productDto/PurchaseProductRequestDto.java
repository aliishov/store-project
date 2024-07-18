package com.raul.order_service.dto.productDto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseProductRequestDto(
        @NotNull
        Integer productId,

        @NotNull
        @Positive(message = "Quantity should be greater than 0")
        Integer quantity
) { }
