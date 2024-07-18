package com.raul.order_service.dto.orderDto;

import com.raul.order_service.dto.productDto.PurchaseProductRequestDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequestDto(
        @NotEmpty(message = "Buy at least one product")
        List<PurchaseProductRequestDto> products,
        @NotNull
        Integer appUserId
) { }
