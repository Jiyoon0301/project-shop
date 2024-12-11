package project.shop1.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.shop1.domain.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByAccount(String account);

    Optional<UserEntity> findById(Long id);

    Optional<UserEntity> findByName(String name);

    Optional<UserEntity> findByEmail(String email);
}
