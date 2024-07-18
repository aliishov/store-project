package com.raul.review_service.clients;

import com.raul.review_service.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductClient {
    @GetMapping("/{productId}")
    ResponseEntity<ProductResponseDto> getProductById(@PathVariable Integer productId);
}
