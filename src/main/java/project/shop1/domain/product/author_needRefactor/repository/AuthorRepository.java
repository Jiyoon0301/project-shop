package project.shop1.domain.product.author_needRefactor.repository;

import project.shop1.domain.product.entity.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findAuthorByName(String authorName);
}
