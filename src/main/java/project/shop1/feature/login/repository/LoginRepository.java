package project.shop1.feature.login.repository;


import project.shop1.entity.UserEntity;

import java.util.Optional;

public interface LoginRepository{
    Optional<UserEntity> findUserEntityByUserId(String userId);
}
