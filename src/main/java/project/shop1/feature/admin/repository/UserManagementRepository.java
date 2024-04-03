package project.shop1.feature.admin.repository;

import project.shop1.entity.UserEntity;
import project.shop1.feature.admin.dto.userDto.FindUserEntityByAccountRequestDto;

import java.util.Optional;

public interface UserManagementRepository {

    /* 회원 관리 */
    void userManagement(UserEntity userEntity);

    /* account로 userEntity 1명 찾기 */
    Optional<UserEntity> findUserEntityByAccount(String account);


    }