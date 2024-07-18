package com.raul.review_service.dto;

import java.math.BigDecimal;

public record ProductResponseDto(
        Integer id,
        String name,
        String description,
        Integer quantity,
        BigDecimal price,
        String categoryName
) { }
