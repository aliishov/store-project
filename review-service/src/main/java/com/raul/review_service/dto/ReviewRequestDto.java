package com.raul.review_service.dto;

import jakarta.validation.constraints.*;

public record ReviewRequestDto(

        @Min(value = 0)
        @Max(value = 5)
        Byte rating,

        @NotBlank
        @NotNull
        @NotEmpty
        String text,

        @NotNull
        @Positive
        Integer productId
) { }
