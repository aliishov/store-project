package com.raul.order_service.services.orderServices;

import com.raul.order_service.dto.orderDto.OrderProductsDto;
import com.raul.order_service.dto.orderDto.OrderRequestDto;
import com.raul.order_service.dto.orderDto.OrderResponseDto;
import com.raul.order_service.dto.productDto.PurchaseProductResponseDto;
import com.raul.order_service.models.Order;
import com.raul.order_service.models.OrderProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderConverter {

    public OrderResponseDto convertToOrderResponse(Order order) {

        return new OrderResponseDto(
                order.getId(),
                order.getAppUserId(),
                convertToOrderProductDto(order.getProducts()),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }

    public List<OrderProductsDto> convertToOrderProductDto(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(orderProduct -> new OrderProductsDto(
                        orderProduct.getProductId(),
                        orderProduct.getName(),
                        orderProduct.getQuantity(),
                        orderProduct.getPrice()
                ))
                .collect(Collectors.toList());
    }

    public OrderProduct convertToOrderProduct(PurchaseProductResponseDto purchaseProductResponseDto) {
        return OrderProduct.builder()
                .productId(purchaseProductResponseDto.productId())
                .name(purchaseProductResponseDto.name())
                .quantity(purchaseProductResponseDto.quantity())
                .price(purchaseProductResponseDto.price())
                .build();
    }
}
