package com.raul.product_service.services;

import com.raul.product_service.dto.ProductRequestDto;
import com.raul.product_service.dto.ProductResponseDto;
import com.raul.product_service.dto.PurchaseProductRequestDto;
import com.raul.product_service.models.Product;
import com.raul.product_service.repositories.ProductRepository;
import com.raul.product_service.util.exception.ProductNotFoundException;
import com.raul.product_service.util.exception.PurchaseProductException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductConverter productConverter;

    @InjectMocks
    private ProductService productService;

    @Test
    void getAllProducts_ShouldReturnAllProducts() {
        Product product = new Product(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), null);
        when(productRepository.findAll()).thenReturn(List.of(product));
        ProductResponseDto responseDto = new ProductResponseDto(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), "Category1");
        when(productConverter.convertToProductResponse(product)).thenReturn(responseDto);

        ResponseEntity<List<ProductResponseDto>> response = productService.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDto, response.getBody().get(0));
    }

    @Test
    void getProductById_ShouldReturnProduct_WhenProductExists() {
        Product product = new Product(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), null);
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        ProductResponseDto responseDto = new ProductResponseDto(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), "Category1");
        when(productConverter.convertToProductResponse(product)).thenReturn(responseDto);

        ResponseEntity<ProductResponseDto> response = productService.getProductById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(responseDto, response.getBody());
    }

    @Test
    void getProductById_ShouldThrowException_WhenProductDoesNotExist() {
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1));
    }

    @Test
    void createProduct_ShouldCreateAndReturnProduct() {
        ProductRequestDto requestDto = new ProductRequestDto(null, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), 1);
        Product product = new Product(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), null);
        when(productConverter.convertToProduct(requestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        ProductResponseDto responseDto = new ProductResponseDto(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), "Category1");
        when(productConverter.convertToProductResponse(product)).thenReturn(responseDto);
        when(productRepository.findAll()).thenReturn(List.of(product));

        ResponseEntity<List<ProductResponseDto>> response = productService.createProduct(requestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(responseDto, response.getBody().get(0));
    }

    @Test
    void purchaseProduct_ShouldThrowException_WhenInsufficientQuantity() {
        PurchaseProductRequestDto requestDto = new PurchaseProductRequestDto(1, 15);
        Product product = new Product(1, "Product1", "Description1", 10, BigDecimal.valueOf(100.00), null);
        when(productRepository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of(product));

        assertThrows(PurchaseProductException.class, () -> productService.purchaseProduct(List.of(requestDto)));
    }
}
