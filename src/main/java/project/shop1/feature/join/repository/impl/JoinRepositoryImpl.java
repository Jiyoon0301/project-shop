package project.shop1.feature.join.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.EmailAuth;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.repository.JoinRepository;

import static project.shop1.entity.QUserEntity.userEntity;
import static project.shop1.entity.QEmailAuth.emailAuth;
import java.util.Optional;

@Repository
public class JoinRepositoryImpl implements JoinRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public JoinRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<UserEntity> findUserEntityByAccount(String account){
        UserEntity result = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.account.eq(account))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public void saveUserEntity(UserEntity userEntity){
        entityManager.persist(userEntity);
    }

    @Override
    public void saveEmailAuth(EmailAuth emailAuth){
        entityManager.persist(emailAuth);
    }

    @Override
    public Optional<EmailAuth> findEmailAuthByEmail(String email) {
        EmailAuth result = jpaQueryFactory
                .selectFrom(emailAuth)
                .where(emailAuth.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(result);
    }


}
