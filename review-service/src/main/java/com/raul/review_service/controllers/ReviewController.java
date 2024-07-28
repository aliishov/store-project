package com.raul.review_service.controllers;

import com.raul.review_service.dto.ReviewRequestDto;
import com.raul.review_service.dto.ReviewResponseDto;
import com.raul.review_service.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping("/create")
    public ResponseEntity<ReviewResponseDto> create(@RequestBody @Valid ReviewRequestDto request) {
        return service.create(request);
    }

    @DeleteMapping("/del/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable Integer reviewId) {
        return service.delete(reviewId);
    }

    @GetMapping("/all/{productId}")
    public ResponseEntity<List<ReviewResponseDto>> getAllReviewByProductId(@PathVariable Integer productId) {
        return service.getAllReviewByProductId(productId);
    }
}
