package com.raul.product_service.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PurchaseProductException extends RuntimeException {

    private final String msg;
}
