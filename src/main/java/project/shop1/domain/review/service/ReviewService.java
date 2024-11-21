package project.shop1.domain.review.service;

import project.shop1.domain.review.dto.*;

import java.util.List;

public interface ReviewService {

    /* 리뷰 등록 버튼 */
    void registerReview(RegisterReviewRequestDto registerReviewRequestDto);

    /* 상품에 대한 리뷰 조회 - productId로 */
    List<GetReviewByProductIdResponseDto> getReviewByProductId(GetReviewByProductIdRequestDto getReviewByProductIdRequestDto);

    /* 리뷰 수정 */
    void updateReview(UpdateReviewRequestDto updateReviewRequestDto);

    /* 리뷰 삭제 버튼 */
    void deleteReview(DeleteReviewRequestDto deleteReviewRequestDto);




    }
