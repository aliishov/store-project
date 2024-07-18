package com.raul.user_service.util.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppUserNotFoundException extends RuntimeException {
    private final String msg;
}
