package com.raul.product_service.dto;

import jakarta.validation.constraints.NotNull;

public record PurchaseProductRequestDto(
        @NotNull
        Integer productId,

        @NotNull
        Integer quantity
) { }
