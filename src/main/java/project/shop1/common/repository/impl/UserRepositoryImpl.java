package project.shop1.common.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.common.repository.UserRepository;
import project.shop1.entity.UserEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static project.shop1.entity.QUserEntity.userEntity;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;
    @Autowired
    public UserRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    @Override
    public void save(UserEntity userEntity){
        entityManager.persist(userEntity);
    }

    @Override
    public UserEntity findOne(Long id){
        return entityManager.find(UserEntity.class,id);
    }
    public List<UserEntity> findAll(){
        return jpaQueryFactory
                .selectFrom(userEntity)
                .fetch();
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
    public Optional<UserEntity> findUserEntityById(Long id){
        UserEntity result = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.id.eq(id))
                .fetchOne();
        return Optional.ofNullable(result);
    }
    @Override
    public Optional<UserEntity> findUserEntityByName(String name){
        UserEntity result = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.name.eq(name))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<UserEntity> findUserEntityByEmail(String email){
        UserEntity result = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.email.eq(email))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
