package project.shop1.feature.searchUserTest.repository;

import project.shop1.entity.UserEntity;

import java.util.List;

public interface SearchUserRepository {
    UserEntity findByAccount(String account);


    }
