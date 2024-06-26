package project.shop1.feature.login.local.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.feature.login.local.repository.LocalLoginRepository;

@Repository
public class LocalLoginRepositoryImpl implements LocalLoginRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public LocalLoginRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

}
