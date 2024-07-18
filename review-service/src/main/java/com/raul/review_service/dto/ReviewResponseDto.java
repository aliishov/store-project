package com.raul.review_service.dto;

import java.time.LocalDateTime;

public record ReviewResponseDto(
        Integer id,
        String productName,
        Byte rating,
        String text,
        LocalDateTime createdAt
) { }
