package com.raul.payment_service.services;

import com.raul.payment_service.dto.paymentDto.PaymentRequestDto;
import com.raul.payment_service.mdoels.Payment;
import com.raul.payment_service.repositories.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;

    public ResponseEntity<String> createPayment(PaymentRequestDto request) throws StripeException {
        var params = ChargeCreateParams.builder()
                .setAmount(request.totalPrice().multiply(BigDecimal.valueOf(100)).longValue())
                .setCurrency(request.currency())
                .setSource(request.token())
                .build();

        var charge = Charge.create(params);

        var payment = Payment.builder()
                .totalPrice(request.totalPrice())
                .currency(request.currency())
                .status(charge.getStatus())
                .build();

        repository.save(payment);

        return new ResponseEntity<>("successed", HttpStatus.OK);
    }
}
