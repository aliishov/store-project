package com.raul.product_service.services;

import com.raul.product_service.dto.ProductRequestDto;
import com.raul.product_service.dto.ProductResponseDto;
import com.raul.product_service.dto.PurchaseProductRequestDto;
import com.raul.product_service.dto.PurchaseProductResponseDto;
import com.raul.product_service.models.Category;
import com.raul.product_service.models.Product;
import com.raul.product_service.repositories.ProductRepository;
import com.raul.product_service.util.exception.ProductNotFoundException;
import com.raul.product_service.util.exception.PurchaseProductException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductConverter converter;

    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return new ResponseEntity<>(repository.findAll().stream()
                .map(converter::convertToProductResponse).collect(Collectors.toList())
                , HttpStatus.OK);
    }

    public ResponseEntity<ProductResponseDto> getProductById(Integer productId) {
        return new ResponseEntity<>(repository.findById(productId)
                .map(converter::convertToProductResponse)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID: " + productId + " not found"))
                , HttpStatus.OK);
    }

    public ResponseEntity<List<ProductResponseDto>> createProduct(ProductRequestDto request) {
        var product = converter.convertToProduct(request);

        repository.save(product);

        return new ResponseEntity<>(repository.findAll().stream()
                .map(converter::convertToProductResponse).collect(Collectors.toList())
                , HttpStatus.CREATED);
    }

    public ResponseEntity<List<PurchaseProductResponseDto>> purchaseProduct(List<PurchaseProductRequestDto> requests) {
        var productIds = requests.stream().map(PurchaseProductRequestDto::productId).toList();

        var productsInStock = repository.findAllByIdInOrderById(productIds);

        if (productIds.size() != productsInStock.size()) {
            throw new PurchaseProductException("Some products does not exists");
        }

        var productsInStockRequest = requests.stream()
                .sorted(Comparator.comparing(PurchaseProductRequestDto::productId)).toList();

        var purchasedProducts = new ArrayList<PurchaseProductResponseDto>();

        for(int i = 0; i < productsInStock.size(); i++) {
            var product = productsInStock.get(i);
            var productRequest = productsInStockRequest.get(i);

            if(product.getQuantity() < productRequest.quantity()) {
                throw new PurchaseProductException("an insufficient quantity for product with ID: " + product.getName());
            }

            var updatedQuantity = product.getQuantity() - productRequest.quantity();
            product.setQuantity(updatedQuantity);

            repository.save(product);

            purchasedProducts.add(converter.convertToPurchaseProductResponse(product, productRequest.quantity()));
        }

        return new ResponseEntity<>(purchasedProducts, HttpStatus.OK);
    }

    public ResponseEntity<List<ProductResponseDto>> deleteProductById(Integer productId) {
        var product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID: " + productId + " not found"));

        repository.delete(product);

        return new ResponseEntity<>(repository.findAll().stream()
                .map(converter::convertToProductResponse).collect(Collectors.toList())
                , HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ProductResponseDto> updateProduct(Integer productId, ProductRequestDto request) {
        var product = repository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with ID: " + productId + " not found"));

        updateProductFromRequest(product, request);

        repository.save(product);

        return new ResponseEntity<>(converter.convertToProductResponse(product), HttpStatus.ACCEPTED);
    }

    private void updateProductFromRequest(Product product, ProductRequestDto request) {
        if (StringUtils.isNotBlank(request.name())) {
            product.setName(request.name());
        }
        if (StringUtils.isNotBlank(request.description())) {
            product.setDescription(request.description());
        }
        product.setQuantity(request.quantity());
        product.setPrice(request.price());
        product.setCategory(Category.builder()
                .id(request.category_id())
                .build());
    }
}
