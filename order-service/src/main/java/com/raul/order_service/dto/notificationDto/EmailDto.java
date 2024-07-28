package com.raul.order_service.dto.notificationDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDto {
    private String to;
    private String subject;
    private String body;
}
