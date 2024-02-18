package project.shop1.common.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.shop1.entity.UserEntity;

import java.util.List;

import static project.shop1.entity.QUserEntity.userEntity;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    private JPAQueryFactory jpaQueryFactory;

    public void save(UserEntity userEntity){
        em.persist(userEntity);
    }
    public UserEntity findOne(Long id){
        return em.find(UserEntity.class,id);
    }
    public List<UserEntity> findAll(){
        return jpaQueryFactory
                .selectFrom(userEntity)
                .fetch();
    }
//    public List<UserEntity> findByName(String name){}
}
