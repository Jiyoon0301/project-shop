package project.shop1.feature.review.repository;

import project.shop1.entity.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository {

    /* 리뷰 저장 */
    void saveReview(Review review);

    /* 상품에 대한 리뷰 조회 - productId로 */
    List<Review> findReviewByProductId(Long productId, int page);

    /* 회원의 중복 리뷰 확인 */
    Optional<Review> findReviewByProductIdAndUserEntityAccount(Long productId, String account);

    /* 리븁 수정 등록 버튼 */
    void updateReview(Long productId, String account, String content, double rating, LocalDateTime regDate);

    /* 리뷰 삭제 버튼 */
    void deleteReview(Review review);




    }
