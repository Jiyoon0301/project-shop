package project.shop1.domain.review.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.shop1.domain.review.repository.ReviewRepositoryCustom;

import static project.shop1.domain.review.entity.QReview.review;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Double findAverageRatingByProductId(Long productId) {
        return queryFactory
                .select(review.rating.avg())
                .from(review)
                .where(review.book.id.eq(productId))
                .fetchOne();
    }
}
