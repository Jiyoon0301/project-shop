package project.shop1.domain.review_needRefactor.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.shop1.domain.review_needRefactor.service.ReviewService;
import project.shop1.domain.review_needRefactor.dto.*;
import project.shop1.global.util.reponse.BooleanResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /* 리뷰 등록 버튼 */
    @PostMapping("/register-review") //RegisterReviewRequestDto : Long productId, String content, double rating
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> registerReview(@RequestBody RegisterReviewRequestDto registerReviewRequestDto) {
        reviewService.registerReview(registerReviewRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 상품에 대한 리뷰 조회 - productId로 */
    @PostMapping("/get-review-by-product-id") //GetReviewByProductIdRequestDto : Long productId, int page
    public List<GetReviewByProductIdResponseDto> getReviewByProductId(@RequestBody GetReviewByProductIdRequestDto getReviewByProductIdRequestDto) {
        List<GetReviewByProductIdResponseDto> result = reviewService.getReviewByProductId(getReviewByProductIdRequestDto);
        
        return result;
    }

    /* 리뷰 수정 등록 버튼 */
    @PostMapping("/update-review") //UpdateReviewRequestDto : Long productId, String content, double rating
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> updateReview(@RequestBody UpdateReviewRequestDto updateReviewRequestDto) {
        reviewService.updateReview(updateReviewRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }

    /* 리뷰 삭제 */
    @PostMapping("/delete-review") //DeleteReviewRequestDto : Long productId
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BooleanResponse> deleteReview(@RequestBody DeleteReviewRequestDto deleteReviewRequestDto) {
        reviewService.deleteReview(deleteReviewRequestDto);

        return ResponseEntity.ok(BooleanResponse.of(true));
    }
}
