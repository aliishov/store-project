package com.raul.product_service.controllers;

import com.raul.product_service.dto.ProductRequestDto;
import com.raul.product_service.dto.ProductResponseDto;
import com.raul.product_service.dto.PurchaseProductRequestDto;
import com.raul.product_service.dto.PurchaseProductResponseDto;
import com.raul.product_service.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Integer productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("/create")
    public ResponseEntity<List<ProductResponseDto>> createProduct(@RequestBody @Valid ProductRequestDto request) {
        return productService.createProduct(request);
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<PurchaseProductResponseDto>> purchaseProduct(@RequestBody List<PurchaseProductRequestDto> requests) {
        return productService.purchaseProduct(requests);
    }

    @DeleteMapping("del/{productId}")
    public ResponseEntity<List<ProductResponseDto>> deleteProductById(@PathVariable Integer productId) {
        return productService.deleteProductById(productId);
    }

    @PutMapping("update/{productId}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Integer productId,
                                                            @RequestBody ProductRequestDto request) {
        return productService.updateProduct(productId, request);
    }
}
