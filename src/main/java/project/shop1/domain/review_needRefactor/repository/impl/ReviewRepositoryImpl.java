package project.shop1.domain.review_needRefactor.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.domain.product.entity.Review;
import project.shop1.domain.review_needRefactor.repository.ReviewRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static project.shop1.entity.QReview.review;


@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public ReviewRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* 리뷰 저장 */
    @Override
    public void saveReview(Review review){
        entityManager.persist(review);
    }

    /* 상품에 대한 리뷰 조회 - productId로 */
    @Override
    public List<Review> findReviewByProductId(Long productId, int page) {
        List<Review> result = jpaQueryFactory
                .selectFrom(review)
                .where(review.book.id.eq(productId))
                .offset(10*(page-1))
                .limit(10)
                .fetch();
        return result;
    }

    /* 회원의 중복 리뷰 확인 */
    @Override
    public Optional<Review> findReviewByProductIdAndUserEntityAccount(Long productId, String account) {
        Review result = jpaQueryFactory
                .selectFrom(review)
                .where(review.book.id.eq(productId)
                .and(review.userEntity.account.eq(account)))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    /* 리뷰 수정 등록 버튼 */
    @Override
    public void updateReview(Long productId, String account, String content, double rating, LocalDateTime regDate) {
        long count = jpaQueryFactory
                .update(review)
                .where(review.book.id.eq(productId)
                        .and(review.userEntity.account.eq(account)))
                .set(review.content, content)
                .set(review.rating, rating)
                .set(review.regDate, regDate)
                .execute();
    }

    /* 리뷰 삭제 버튼 */
    @Override
    public void deleteReview(Review review){
        entityManager.remove(review);
    }



}
