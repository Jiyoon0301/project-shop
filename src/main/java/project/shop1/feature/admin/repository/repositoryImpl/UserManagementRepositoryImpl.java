package project.shop1.feature.admin.repository.repositoryImpl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.UserEntity;
import project.shop1.feature.admin.repository.UserManagementRepository;


import static project.shop1.entity.QUserEntity.userEntity;
import java.util.Optional;


@Repository
public class UserManagementRepositoryImpl implements UserManagementRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public UserManagementRepositoryImpl(EntityManager entityManager){
        this.entityManager=entityManager;
        this.jpaQueryFactory=new JPAQueryFactory(entityManager);
    }

    /* User 관리*/
    @Override
    public void userManagement(UserEntity userEntity) {
    }

    /* account 로 userEntity 1명 찾기 */
    @Override
    public Optional<UserEntity> findUserEntityByAccount(String account) {
        UserEntity findUser = jpaQueryFactory
                .selectFrom(userEntity)
                .where(userEntity.account.eq(account))
                .fetchOne();
        return Optional.ofNullable(findUser);
    }

}
