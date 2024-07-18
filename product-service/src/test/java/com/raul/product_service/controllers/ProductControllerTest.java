package com.raul.product_service.controllers;

import com.raul.product_service.dto.ProductRequestDto;
import com.raul.product_service.dto.ProductResponseDto;
import com.raul.product_service.dto.PurchaseProductRequestDto;
import com.raul.product_service.dto.PurchaseProductResponseDto;
import com.raul.product_service.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        ProductResponseDto responseDto = new ProductResponseDto(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), "Category1");
        when(productService.getAllProducts()).thenReturn(new ResponseEntity<>(List.of(responseDto), HttpStatus.OK));

        ResponseEntity<List<ProductResponseDto>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(responseDto, response.getBody().get(0));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        ProductResponseDto responseDto = new ProductResponseDto(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), "Category1");
        when(productService.getProductById(1)).thenReturn(new ResponseEntity<>(responseDto, HttpStatus.OK));

        ResponseEntity<ProductResponseDto> response = productController.getProductById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void createProduct_ShouldCreateAndReturnProduct() {
        ProductRequestDto requestDto = new ProductRequestDto(null, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), 1);
        ProductResponseDto responseDto = new ProductResponseDto(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), "Category1");
        when(productService.createProduct(requestDto)).thenReturn(new ResponseEntity<>(List.of(responseDto), HttpStatus.CREATED));

        ResponseEntity<List<ProductResponseDto>> response = productController.createProduct(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(responseDto, response.getBody().get(0));
    }

    @Test
    void purchaseProduct_ShouldPurchaseAndReturnProducts() {
        PurchaseProductRequestDto requestDto = new PurchaseProductRequestDto(1, 5);
        PurchaseProductResponseDto responseDto = new PurchaseProductResponseDto(1, "Product1", "Description1", BigDecimal.valueOf(100.00), 5);
        when(productService.purchaseProduct(List.of(requestDto))).thenReturn(new ResponseEntity<>(List.of(responseDto), HttpStatus.OK));

        ResponseEntity<List<PurchaseProductResponseDto>> response = productController.purchaseProduct(List.of(requestDto));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
        assertEquals(responseDto, response.getBody().get(0));
    }
}
