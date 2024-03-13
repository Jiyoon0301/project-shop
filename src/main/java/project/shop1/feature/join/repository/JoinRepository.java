package project.shop1.feature.join.repository;

import project.shop1.entity.EmailAuth;
import project.shop1.entity.UserEntity;

import java.util.Optional;

public interface JoinRepository {

    Optional<UserEntity> findUserEntityByAccount(String account);

    void saveUserEntity(UserEntity userEntity);

    void saveEmailAuth(EmailAuth emailAuth);

    Optional<EmailAuth> findEmailAuthByEmail(String email);
}
