package project.shop1.feature.join.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.UserEntity;
import project.shop1.feature.join.repository.JoinRepository;

import static project.shop1.entity.QUserEntity.userEntity;

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
    public Optional<UserEntity> findUserEntityByUserId(String userId){ //유저 아이디를 조회해서 반환(중복된 회원이 있는지 검사)
        UserEntity result = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.userId.eq(userId))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public void saveUserEntity(UserEntity userEntity){
        entityManager.persist(userEntity);
    }


}
