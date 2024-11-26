package project.shop1.domain.review_needRefactor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.review_needRefactor.entity.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findById(Long productId, int page);

    List<Review> findReviewsByProductId(Long productId, int page);

    //특정 유저의 해당 상품에 대한 리뷰
    Optional<Review> findReviewByProductAndUser(Long productId, String account);

    // 리뷰 수정
    void updateReview(Long productId, String account, String content, double rating, LocalDateTime regDate);

    // 리뷰 삭제 버튼
    void deleteReview(Review review);
}
