package project.shop1.feature.admin.repository;

import project.shop1.entity.UserEntity;

public interface UserManagementRepository {

    /* 회원 관리 */
    void userManagement(UserEntity userEntity);

}