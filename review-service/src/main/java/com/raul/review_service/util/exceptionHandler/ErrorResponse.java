package com.raul.review_service.util.exceptionHandler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) { }
