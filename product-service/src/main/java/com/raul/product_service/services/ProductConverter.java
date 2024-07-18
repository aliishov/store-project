package com.raul.product_service.services;

import com.raul.product_service.dto.ProductRequestDto;
import com.raul.product_service.dto.ProductResponseDto;
import com.raul.product_service.dto.PurchaseProductResponseDto;
import com.raul.product_service.models.Category;
import com.raul.product_service.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductConverter {
    public Product convertToProduct(ProductRequestDto request) {
        return Product.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .quantity(request.quantity())
                .price(request.price())
                .category(Category.builder()
                        .id(request.category_id())
                        .build())
                .build();
    }

    public ProductResponseDto convertToProductResponse(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory().getName()
        );
    }

    public PurchaseProductResponseDto convertToPurchaseProductResponse(Product product, Integer quantity) {
        return new PurchaseProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
