package project.shop1.feature.login.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import project.shop1.entity.QUserEntity;
import project.shop1.entity.UserEntity;
import project.shop1.feature.login.dto.LoginRequestDto;
import project.shop1.feature.login.repository.LoginRepository;

import java.util.Optional;

import static project.shop1.entity.QUserEntity.userEntity;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public LoginRepositoryImpl(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }


}
