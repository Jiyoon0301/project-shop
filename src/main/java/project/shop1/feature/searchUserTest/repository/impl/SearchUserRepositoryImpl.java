package project.shop1.feature.searchUserTest.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.UserEntity;
import project.shop1.feature.searchUserTest.repository.SearchUserRepository;

import static project.shop1.entity.QUserEntity.userEntity;


@Repository
public class SearchUserRepositoryImpl implements SearchUserRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SearchUserRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    @Override
    public UserEntity findByAccount(String account) {
        UserEntity result = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.account.eq(account))
                .fetchOne();
        return result;
    }
}
