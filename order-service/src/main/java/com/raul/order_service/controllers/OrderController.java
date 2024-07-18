package com.raul.order_service.controllers;

import com.raul.order_service.dto.orderDto.OrderRequestDto;
import com.raul.order_service.dto.orderDto.OrderResponseDto;
import com.raul.order_service.services.orderServices.OrderService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto request) {
        return orderService.createOrder(request);
    }

    @PutMapping("/{orderId}/update")
    public ResponseEntity<OrderResponseDto> updateOrder(@PathVariable Integer orderId) throws StripeException {
        return orderService.updateOrder(orderId);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderResponseDto>> getAll() {
        return orderService.getAll();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Integer orderId) {
        return orderService.getOrderById(orderId);
    }

}
