package project.shop1.feature.admin.service;

import project.shop1.entity.UserEntity;
import project.shop1.feature.admin.dto.userDto.FindUserEntityByAccountRequestDto;

public interface UserManagementService {


    /* 작가 상세 정보 */
//    public Optional<Author> authorGetDetail(String authorName);

    /* account로 userEntity 한 명 찾기 */
    UserEntity findUserEntityByAccount(FindUserEntityByAccountRequestDto findUserEntityByAccountRequestDto);







    }