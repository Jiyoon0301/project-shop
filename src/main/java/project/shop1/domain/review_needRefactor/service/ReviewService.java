package project.shop1.domain.review_needRefactor.service;

import project.shop1.domain.review_needRefactor.dto.*;

import java.util.List;

public interface ReviewService {

    // 리뷰 등록
    void registerReview(ReviewRequestDto reviewRequestDto);

    // 특정 상품에 대한 리뷰 조회
    List<GetReviewsResponseDto> getReviewsByProduct(Long id, GetReviewsRequestDto getReviewsRequestDto);

    // 특정 사용자의 리뷰 조회
    List<GetReviewsResponseDto> getReviewsByUser(Long userId);

    // 리뷰 수정
    void updateReview(ReviewRequestDto reviewRequestDto);

    // 리뷰 삭제
    void deleteReview(Long reviewId);

    // 상품 리뷰 평균 별점 조회
    double getAverageRating(Long productId);
}
