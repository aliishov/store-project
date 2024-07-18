package com.raul.order_service.services.orderServices;

import com.raul.order_service.clients.AppUserClient;
import com.raul.order_service.clients.PaymentClient;
import com.raul.order_service.clients.ProductClient;
import com.raul.order_service.dto.orderDto.OrderRequestDto;
import com.raul.order_service.dto.orderDto.OrderResponseDto;
import com.raul.order_service.dto.paymentDto.PaymentRequestDto;
import com.raul.order_service.models.Order;
import com.raul.order_service.repositories.OrderRepository;
import com.raul.order_service.util.exception.AppUserNotFoundException;
import com.raul.order_service.util.exception.OrderNotFoundException;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final AppUserClient appUserClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderConverter converter;
    private final PaymentClient paymentClient;

    @Transactional
    public ResponseEntity<OrderResponseDto> createOrder(OrderRequestDto request){

        var appUser = appUserClient.getAppUserById(request.appUserId()).getBody();

        if (appUser == null) {
            throw new AppUserNotFoundException("Cannot create Order. " +
                                                "Not found user with ID: " + request.appUserId());
        }

        var purchasedProduct = productClient.purchaseProduct(request.products()).getBody();

        assert purchasedProduct != null;
        var orderProducts = purchasedProduct.stream()
                .map(converter::convertToOrderProduct)
                .collect(Collectors.toList());

        var totalPrice = orderProducts.stream()
                .map(orderProduct -> orderProduct.getPrice().multiply(BigDecimal.valueOf(orderProduct.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        var order = Order.builder()
                .appUserId(appUser.id())
                .products(orderProducts)
                .totalPrice(totalPrice)
                .status("UNPAYED")
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(order);

        return new ResponseEntity<>(converter.convertToOrderResponse(order), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<OrderResponseDto> updateOrder(Integer orderId) throws StripeException {
        var order = repository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID: " + orderId + " not found"));

        var payment = paymentClient.createPayment(
                new PaymentRequestDto(
                        "tok_visa",
                        order.getTotalPrice(),
                        "usd",
                        appUserClient.getAppUserById(order.getAppUserId()).getBody()
                )
        );

        if (payment.getStatusCode() == HttpStatus.OK) {
            order.setStatus("PAYED");
            repository.save(order);

        } else {
            return new ResponseEntity<>(converter.convertToOrderResponse(order), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(converter.convertToOrderResponse(order), HttpStatus.OK);
    }

    public ResponseEntity<List<OrderResponseDto>> getAll() {
        return new ResponseEntity<>(repository.findAll().stream()
                .map(converter::convertToOrderResponse).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    public ResponseEntity<OrderResponseDto> getOrderById(Integer orderId) {
        return new ResponseEntity<>(repository.findById(orderId)
                .map(converter::convertToOrderResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order with ID: " + orderId + " not found"))
                , HttpStatus.OK);
    }
}
