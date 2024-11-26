package project.shop1.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.review.entity.Review;
import project.shop1.domain.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    List<Review> findById(Long productId, int page);

    List<Review> findByProductId(Long productId);

    //특정 유저의 해당 상품에 대한 리뷰
    Optional<Review> findReviewByProductAndUser(Long productId, String account);

    // 리뷰 수정
    void updateReview(Long productId, String account, String content, double rating, LocalDateTime regDate);

    // 리뷰 삭제
    void deleteReview(Review review);

    // 제품 ID로 필터링하고 평점은 옵션으로 추가 (null 가능)
    Page<Review> findByProductIdAndRating(Long productId, Integer rating, Pageable pageable);

    List<Review> findByUser(UserEntity userEntity);
}
