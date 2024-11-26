package project.shop1.domain.review.repository;

public interface ReviewRepositoryCustom {
    Double findAverageRatingByProductId(Long productId);
}
