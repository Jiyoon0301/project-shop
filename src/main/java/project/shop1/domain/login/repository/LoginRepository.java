package project.shop1.domain.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.user.entity.UserEntity;

public interface LoginRepository extends JpaRepository<UserEntity, Long> {
}
