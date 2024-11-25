package project.shop1.domain.product.author_needRefactor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shop1.domain.product.entity.Author;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findById(Long Id);
    Optional<Author> findAuthorByName(String authorName);
}
