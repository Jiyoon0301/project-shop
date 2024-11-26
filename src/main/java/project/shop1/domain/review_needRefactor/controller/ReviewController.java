package project.shop1.domain.review_needRefactor.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.shop1.domain.review_needRefactor.service.ReviewService;
import project.shop1.domain.review_needRefactor.dto.*;
import project.shop1.global.util.reponse.BooleanResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     * @param reviewRequestDto: Long productId, Long userId, String content, double rating
     * @return
     */
    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> createReview(@Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.createReview(reviewRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /**
     * 특정 상품에 대한 리뷰 조회
     * @param productId
     * @param getReviewsRequestDto: Integer rating, int page, int size, String sortBy
     * @return Long id, String content, int rating, String userName, LocalDateTime regDate
     */
    @PostMapping("/product/{productId}")
    public ResponseEntity<Page<GetReviewsResponseDto>> getReviewByProduct(@PathVariable Long productId,
                                                                    @RequestBody GetReviewsRequestDto getReviewsRequestDto) {
        Page<GetReviewsResponseDto> reviews = reviewService.getReviewsByProduct(productId, getReviewsRequestDto);
        return ResponseEntity.ok(reviews);
    }

    /**
     * 특정 사용자 리뷰 조회
     * @param userId
     * @return String content, double rating, LocalDateTime regDate;
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetReviewsResponseDto>> getReviewsByUser(@PathVariable Long userId) {
        List<GetReviewsResponseDto> reviews = reviewService.getReviewsByUser(userId);
        return ResponseEntity.ok(reviews);
    }

    /**
     * 리뷰 수정
     * @param reviewRequestDto: Long productId, Long userId, String content, double rating
     * @return
     */
    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewService.updateReview(reviewId, reviewRequestDto);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
    @PostMapping("/{reviewId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /**
     * 상품 리뷰 평균 별점 조회
     * @param productId
     * @return
     */
    @GetMapping("/product/{productId}/average")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        double averageRating = reviewService.getAverageRating(productId);
        return ResponseEntity.ok(averageRating);
    }
}
