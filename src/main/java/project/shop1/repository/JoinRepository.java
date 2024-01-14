package project.shop1.repository;

import project.shop1.entity.UserEntity;

import java.util.Optional;

public interface JoinRepository {

    Optional<UserEntity> findUserEntityByName(String name);

    void saveUserEntity(UserEntity userEntity);

}
