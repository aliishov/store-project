package com.raul.order_service.clients;

import com.raul.order_service.dto.productDto.PurchaseProductRequestDto;
import com.raul.order_service.dto.productDto.PurchaseProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {

    @PostMapping("/purchase")
    ResponseEntity<List<PurchaseProductResponseDto>> purchaseProduct(@RequestBody List<PurchaseProductRequestDto> requests);
}
