package project.shop1.domain.emailAuth.repository;

import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.shop1.domain.emailAuth.entity.EmailAuth;

import java.util.Optional;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, Id> {
    Optional<EmailAuth> findByEmail(String email);
}
