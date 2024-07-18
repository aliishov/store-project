package com.raul.review_service.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ReviewNotFoundException extends RuntimeException {

    private final String msg;
}
