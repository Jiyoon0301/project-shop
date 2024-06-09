package project.shop1.feature.myPage.getAllReviews.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.Review;
import project.shop1.feature.myPage.getAllReviews.repository.GetAllReviewsRepository;

import java.util.List;
import static project.shop1.entity.QReview.review;

@Repository
public class GetAllReviewsRepositoryImpl implements GetAllReviewsRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public GetAllReviewsRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }


}
