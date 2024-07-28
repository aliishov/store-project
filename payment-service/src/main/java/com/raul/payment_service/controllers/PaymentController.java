package com.raul.payment_service.controllers;

import com.raul.payment_service.dto.paymentDto.PaymentRequestDto;
import com.raul.payment_service.services.PaymentService;
import com.stripe.exception.StripeException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody @Valid PaymentRequestDto request) throws StripeException {
        return paymentService.createPayment(request);
    }
}
