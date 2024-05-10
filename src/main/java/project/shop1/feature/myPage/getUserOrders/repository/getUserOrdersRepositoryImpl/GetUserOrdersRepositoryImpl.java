package project.shop1.feature.myPage.getUserOrders.repository.getUserOrdersRepositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.feature.myPage.getUserOrders.repository.GetUserOrdersRepository;

@Repository
public class GetUserOrdersRepositoryImpl implements GetUserOrdersRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public GetUserOrdersRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }
}
