package com.raul.order_service.clients;

import com.raul.order_service.dto.paymentDto.PaymentRequestDto;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "payment-service", url = "${payment.service.url}")
public interface PaymentClient {

    @PostMapping
    ResponseEntity<String> createPayment(@RequestBody @Valid PaymentRequestDto request) throws StripeException;
}
