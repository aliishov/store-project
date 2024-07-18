package com.raul.review_service.services;

import com.raul.review_service.dto.ReviewRequestDto;
import com.raul.review_service.dto.ReviewResponseDto;
import com.raul.review_service.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewConverter {

    public Review convertToReview(ReviewRequestDto request) {
        return Review.builder()
                .rating(request.rating())
                .text(request.text())
                .productId(request.productId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
