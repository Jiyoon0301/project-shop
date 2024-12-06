package project.shop1.domain.review.service;

import org.springframework.data.domain.Page;
import project.shop1.domain.review.dto.request.GetReviewsRequestDto;
import project.shop1.domain.review.dto.request.ReviewRequestDto;
import project.shop1.domain.review.dto.response.GetReviewsResponseDto;

import java.util.List;

public interface ReviewService {

    // 리뷰 등록
    void createReview(ReviewRequestDto reviewRequestDto);

    // 특정 상품에 대한 리뷰 조회
    Page<GetReviewsResponseDto> getReviewsByProduct(Long id, GetReviewsRequestDto getReviewsRequestDto);

    // 특정 사용자의 리뷰 조회
    List<GetReviewsResponseDto> getReviewsByUser(Long userId);

    // 리뷰 수정
    void updateReview(Long reviewId, ReviewRequestDto reviewRequestDto);

    // 리뷰 삭제
    void deleteReview(Long reviewId);

    // 상품 리뷰 평균 별점 조회
    double getAverageRating(Long productId);
}
