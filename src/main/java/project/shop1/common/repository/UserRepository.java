package project.shop1.common.repository;

import project.shop1.entity.UserEntity;

import java.util.Optional;

public interface UserRepository {

    /* userEntity 저장 */
    void save(UserEntity userEntity);

    /* id로 한 명 찾기 */
    UserEntity findOne(Long id);

    /* account로 한 명 찾기 */
    Optional<UserEntity> findUserEntityByAccount(String account);

    /* id로 한 명 찾기 */
    Optional<UserEntity> findUserEntityById(Long id);

    /* 이름으로 한 명 찾기 */
    Optional<UserEntity> findUserEntityByName(String name);

    /* email로 한 명 찾기 */
    Optional<UserEntity> findUserEntityByEmail(String email);





    }
