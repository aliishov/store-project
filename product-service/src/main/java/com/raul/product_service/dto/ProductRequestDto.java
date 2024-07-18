package com.raul.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDto(
        Integer id,

        @NotNull(message = "Name should not be empty")
        String name,

        @NotNull(message = "Description should not be empty")
        String description,

        @Positive(message = "Quantity should be greater than 0")
        Integer quantity,

        @Positive(message = "Price should be greater than 0,00")
        BigDecimal price,

        @NotNull(message = "Category should not be empty")
        Integer category_id
) { }
