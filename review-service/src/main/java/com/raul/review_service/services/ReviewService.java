package com.raul.review_service.services;

import com.raul.review_service.clients.ProductClient;
import com.raul.review_service.dto.ReviewRequestDto;
import com.raul.review_service.dto.ReviewResponseDto;
import com.raul.review_service.model.Review;
import com.raul.review_service.repositories.ReviewRepository;
import com.raul.review_service.util.exception.ProductNotFoundException;
import com.raul.review_service.util.exception.ReviewNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ProductClient productClient;
    private final ReviewRepository repository;
    private final ReviewConverter converter;
    public ResponseEntity<ReviewResponseDto> create(ReviewRequestDto request) {

        var product = productClient.getProductById(request.productId())
                .getBody();

        if (product == null) {
            throw new ProductNotFoundException("Product with ID: " + request.productId() + " not found");
        }

        var review = repository.save(converter.convertToReview(request));

        var response = new ReviewResponseDto(
                review.getId(),
                product.name(),
                review.getRating(),
                review.getText(),
                review.getCreatedAt()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    public ResponseEntity<String> delete(Integer reviewId) {

        var review = repository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review with ID: " + reviewId + " not found"));

        repository.delete(review);

        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    public ResponseEntity<List<ReviewResponseDto>> getAllReviewByProductId(Integer productId) {

        var product = productClient.getProductById(productId)
                .getBody();

        if (product == null) {
            throw new ProductNotFoundException("Product with ID: " + productId + " not found");
        }

        var reviews = repository.findAllByProductId(productId);

        List<ReviewResponseDto> responses = new ArrayList<>();

        for (Review review : reviews) {
            var response = new ReviewResponseDto(
                    review.getId(),
                    product.name(),
                    review.getRating(),
                    review.getText(),
                    review.getCreatedAt()
            );
            responses.add(response);
        }

        return new ResponseEntity<>(responses, HttpStatus.OK);
    }
}
