package project.shop1.domain.review_needRefactor.repository;

public interface ReviewRepositoryCustom {
    Double findAverageRatingByProductId(Long productId);
}
